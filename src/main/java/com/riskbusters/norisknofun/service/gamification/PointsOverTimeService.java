package com.riskbusters.norisknofun.service.gamification;

import com.riskbusters.norisknofun.domain.*;
import com.riskbusters.norisknofun.repository.gamification.PointsOverTimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing {@link PointsOverTime}.
 */
@Service
@Transactional
public class PointsOverTimeService {

    private final Logger log = LoggerFactory.getLogger(PointsOverTimeService.class);

    private final PointsOverTimeRepository pointsOverTimeRepository;
    private UserGamificationService userGamificationService;

    public PointsOverTimeService(PointsOverTimeRepository pointsOverTimeRepository, @Lazy UserGamificationService userGamificationService) {
        this.pointsOverTimeRepository = pointsOverTimeRepository;
        this.userGamificationService = userGamificationService;
    }

    /**
     * Get all points over time for one user
     *
     * @param user for which the points over time should be returned.
     * @return the list of points
     */
    public List<PointsWithDate> getAllPointsOverTimeForOneUser(User user) {
        log.debug("Request to get points over time for user: {}", user);

        List<PointsOverTime> allPointsOverTimeRowsFromDB = pointsOverTimeRepository.findAllByUserId(user.getId());
        List<PointsWithDate> allPointsOverTime = new ArrayList<>();
        for (PointsOverTime item : allPointsOverTimeRowsFromDB) {
            allPointsOverTime.add(new PointsWithDate(item.getPointsAtThisDay().getPointsAsLong(), item.getDate()));
        }
        return allPointsOverTime;
    }

    /**
     * Get one points value based on date and user
     *
     * @param user for which the points over time should be returned.
     * @param date for which the points over time should be returned.
     * @return the list of points
     */
    public PointsWithDate getPointsByUserAndDate(User user, CustomDate date) {
        log.debug("Request to get points over time for user: {} and date {}", user, date);
        createIfNotExists(user, date);
        PointsOverTime neededPointDBEntry = pointsOverTimeRepository.findAllByUserIdAndDate(user.getId(), date);
        return new PointsWithDate(neededPointDBEntry.getPointsAtThisDay().getPointsAsLong(), neededPointDBEntry.getDate());
    }

    /**
     * Add amount of points for user at the current date.
     *
     * @param points amount of points to add.
     * @param user   the user to add points for.
     * @return the new amount of points.
     */
    public Long addPointsForToday(Points points, User user) {
        log.debug("Request to add {} points for user with id {}", points.getPointsAsLong(), user.getId());
        createIfNotExists(user, new CustomDate());

        PointsOverTime pointsOverTimeForUser = pointsOverTimeRepository.findAllByUserIdAndDate(user.getId(), new CustomDate());

        log.debug("Points at current day before adding points {}", pointsOverTimeForUser);
        Points newPointsValue = pointsOverTimeForUser.addPointsForCurrentDay(points);
        pointsOverTimeRepository.save(pointsOverTimeForUser);
        log.debug("Points at current day after adding {} points: {}", points.getPointsAsLong(), pointsOverTimeForUser);
        userGamificationService.calculateActivityScoreBasedOnPoints(user);
        log.debug("Recalculate and store activityScoreBasedOnPoints for user");

        return newPointsValue.getPointsAsLong();
    }

    private void createIfNotExists(User user, CustomDate date) {
        if (isNull(user, date)) {
            createEntry(user, date);
        }
    }

    private boolean isNull(User user, CustomDate date) {
        PointsOverTime pointsOverTimeForUser = pointsOverTimeRepository.findAllByUserIdAndDate(user.getId(), date);
        return pointsOverTimeForUser == null;
    }

    private void createEntry(User user, CustomDate date) {
        PointsOverTime pointsOneDay = new PointsOverTime(user, date);
        pointsOverTimeRepository.save(pointsOneDay);
    }
}
