package com.riskbusters.norisknofun.service.gamification;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.PointsWithDate;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        List<PointsWithDate> pointsOverTimeAsList = pointsOverTimeService.getAllPointsOverTimeForOneUser(user);

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
        if (userGamification.isPresent()) {
            return Optional.ofNullable(mapper.toUserGamificationDTO(userGamification.get()));
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

        // TODO: refactor process of getting points after discussion day vs. week
        // hacky to get Date yesterday
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterdayDate = calendar.getTime();

        PointsWithDate today = pointsOverTimeService.getPointsByUserAndDate(user, new CustomDate());
        PointsWithDate yesterday = pointsOverTimeService.getPointsByUserAndDate(user, new CustomDate(yesterdayDate));

        Double calculatedActivityScoreBasedOnPoints = slopeCalculation.calculateSlopeBetweenTwoPoints(yesterday, today);

        log.debug("Calculated and stored activityScoreBasedOnPoints (result = {}) for user with id {} for olderPoints: {} and youngerPoint: {}", calculatedActivityScoreBasedOnPoints, user.getId(), yesterday, today);

        Optional<UserGamification> userGamification = userGamificationRepository.findOneWithEagerRelationships(user);
        if (userGamification.isPresent()) {
            userGamification.get().setActivityScoreBasedOnPoints(calculatedActivityScoreBasedOnPoints);
            userGamificationRepository.save(userGamification.get());
        } else {
            UserGamification newUserGamification = new UserGamification(user, calculatedActivityScoreBasedOnPoints);
            userGamificationRepository.save(newUserGamification);
        }
    }
}
