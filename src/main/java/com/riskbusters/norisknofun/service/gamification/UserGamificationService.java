package com.riskbusters.norisknofun.service.gamification;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.PointWithDate;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.domain.achievements.Achievement;
import com.riskbusters.norisknofun.domain.activityscore.SlopeCalculation;
import com.riskbusters.norisknofun.domain.activityscore.SlopeCalculationImpl;
import com.riskbusters.norisknofun.repository.UserRepository;
import com.riskbusters.norisknofun.repository.achievements.AchievementBaseRepository;
import com.riskbusters.norisknofun.repository.gamification.UserGamificationRepository;
import com.riskbusters.norisknofun.service.AchievementService;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;
import com.riskbusters.norisknofun.service.mapper.UserGamificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserGamification}.
 */
@Service
@Transactional
public class UserGamificationService {

    private final Logger log = LoggerFactory.getLogger(UserGamificationService.class);

    private final UserGamificationRepository userGamificationRepository;

    private final PointsOverTimeService pointsOverTimeService;

    private final AchievementBaseRepository achievementBaseRepository;

    private final UserRepository userRepository;

    private final AchievementService achievementService;

    private SlopeCalculation slopeCalculation;

    private UserGamificationMapper mapper;

    public UserGamificationService(UserGamificationRepository userGamificationRepository, AchievementBaseRepository achievementBaseRepository, AchievementService achievementService, UserRepository userRepository, PointsOverTimeService pointsOverTimeService) {
        this.userGamificationRepository = userGamificationRepository;
        this.achievementBaseRepository = achievementBaseRepository;
        this.achievementService = achievementService;
        this.userRepository = userRepository;
        this.mapper = new UserGamificationMapper(this.userRepository);
        this.pointsOverTimeService = pointsOverTimeService;
        this.slopeCalculation = new SlopeCalculationImpl();
    }

    /**
     * Save a userGamification.
     *
     * @param userGamificationDTO the entity to save.
     * @return the persisted entity.
     */
    public UserGamificationDTO save(UserGamificationDTO userGamificationDTO, Long userId) {
        log.debug("Request to save UserGamification : {}", userGamificationDTO);
        achievementService.saveAchievements(userGamificationDTO.getUserAchievements());
        userGamificationDTO.setUserId(userId);
        UserGamification userGamification = mapper.userGamificationDTOtoUserGamification(userGamificationDTO);
        log.debug("Converted from DTO to normal object {}", userGamification);
        userGamification = userGamificationRepository.save(userGamification);
        return mapper.toUserGamificationDTO(userGamification);
    }

    /**
     * Get all the userGamifications.
     *
     * @param user the user.
     * @return the list of userGamifications for one specific user.
     */
    public UserGamificationDTO findAllForOneUser(User user) {
        log.debug("Request to get all UserGamifications for user: {}", user);
        calculateActivityScoreBasedOnPoints(user);
        Optional<UserGamification> userGamification = userGamificationRepository.findOneWithEagerRelationships(user);
        List<PointWithDate> pointsOverTimeAsList = pointsOverTimeService.getAllPointsOverTimeForOneUser(user);

        return userGamification.map(gamification -> mapper.toUserGamificationDTO(gamification, pointsOverTimeAsList)).orElse(null);
    }

    /**
     * Get all the userGamifications with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public List<UserGamification> findAllWithEagerRelationships() {
        return userGamificationRepository.findAllWithEagerRelationships();
    }


    /**
     * Get one userGamification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserGamificationDTO> findOne(Long id) {
        log.debug("Request to get UserGamification : {}", id);
        Optional<UserGamification> userGamification = userGamificationRepository.findOneWithEagerRelationships(id);
        calculateActivityScoreBasedOnPoints(userGamification.get().getUser());
        List<PointWithDate> pointsOverTimeAsList = pointsOverTimeService.getAllPointsOverTimeForOneUser(userGamification.get().getUser());
        if (userGamification.isPresent()) {
            return userGamification.map(gamification -> mapper.toUserGamificationDTO(gamification, pointsOverTimeAsList));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Delete the userGamification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserGamification : {}", id);
        userGamificationRepository.deleteById(id);
    }

    public void saveAchievements(List<Achievement> userAchievements) {
        achievementBaseRepository.saveAll(userAchievements);
    }

    /**
     * Calculate the activityScoreBasedOnPoints for a user and store the result in his/her UserGamification object.
     *
     * @param user the user to calculate the activityScore for.
     */
    public void calculateActivityScoreBasedOnPoints(User user) {
        LocalDate today = LocalDate.now();
        LocalDate sixDaysBefore = LocalDate.now().minusDays(6);
        LocalDate sevenDaysBefore = LocalDate.now().minusDays(7);
        LocalDate fourteenDaysBefore = LocalDate.now().minusDays(13);
        List<LocalDate> youngerWeek = sixDaysBefore.datesUntil(today.plusDays(1L)).collect(Collectors.toList());
        List<LocalDate> olderWeek = fourteenDaysBefore.datesUntil(sevenDaysBefore.plusDays(1L)).collect(Collectors.toList());

        List<PointWithDate> pointsYoungerWeek = fillPointWithDateList(user, youngerWeek);
        List<PointWithDate> pointsOlderWeek = fillPointWithDateList(user, olderWeek);

        PointWithDate youngerWeekSummedUp = sumUpWeekPoints(pointsYoungerWeek);
        PointWithDate olderWeekSummedUp = sumUpWeekPoints(pointsOlderWeek);

        Double calculatedActivityScoreBasedOnPoints = slopeCalculation.calculateSlopeBetweenTwoPoints(olderWeekSummedUp, youngerWeekSummedUp);

        log.debug("Calculated and stored activityScoreBasedOnPoints (summed up per week) (result = {}) for user with id {} for olderPoints: {} and youngerPoint: {}", calculatedActivityScoreBasedOnPoints, user.getId(), olderWeekSummedUp, youngerWeekSummedUp);

        Optional<UserGamification> userGamification = userGamificationRepository.findOneWithEagerRelationships(user);
        if (userGamification.isPresent()) {
            userGamification.get().setActivityScoreBasedOnPoints(calculatedActivityScoreBasedOnPoints);
            userGamificationRepository.save(userGamification.get());
        } else {
            UserGamification newUserGamification = new UserGamification(user, calculatedActivityScoreBasedOnPoints);
            userGamificationRepository.save(newUserGamification);
        }
    }

    private List<PointWithDate> fillPointWithDateList(User user, List<LocalDate> dates) {
        List<PointWithDate> pointsOneWeek = new ArrayList<>();
        for (LocalDate localDate : dates) {
            Date date = java.sql.Date.valueOf(localDate);
            pointsOneWeek.add(pointsOverTimeService.getPointsByUserAndDate(user, new CustomDate(date)));
        }
        return pointsOneWeek;
    }

    private PointWithDate sumUpWeekPoints(List<PointWithDate> pointsOfWeek) {
        Double sum = 0.0;
        for (PointWithDate point : pointsOfWeek) {
            sum += point.getPointsScore();
        }
        return new PointWithDate(sum, new CustomDate(java.sql.Date.valueOf(pointsOfWeek.get(pointsOfWeek.size() - 1).getDate())));
    }
}
