package com.riskbusters.norisknofun.service.gamification;

import com.riskbusters.norisknofun.domain.*;
import com.riskbusters.norisknofun.repository.gamification.PointsOverTimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public PointsOverTimeService(PointsOverTimeRepository pointsOverTimeRepository) {
        this.pointsOverTimeRepository = pointsOverTimeRepository;
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
     * Add amount of points for user at the current date.
     *
     * @param points amount of points to add.
     * @param user   the user to add points for.
     * @return the persisted entity.
     */
    public Long addPointsForToday(Points points, User user) {
        log.debug("Request to add {} points for user with id {}", points.getPointsAsLong(), user.getId());
        PointsOverTime pointsOverTimeForUser = pointsOverTimeRepository.findAllByUserIdAndDate(user.getId(), new CustomDate());

        // TODO: implement scheduler to create values and enable better null handling
        if (pointsOverTimeForUser == null) {
            log.debug("Avoid null");
            PointsOverTime pointsOverTimeForCurrentDay = new PointsOverTime();
            pointsOverTimeForCurrentDay.setUser(user);
            pointsOverTimeForCurrentDay.setDate(new CustomDate());
            pointsOverTimeForCurrentDay.setPointsAtThisDay(new Points(0L));
            pointsOverTimeRepository.save(pointsOverTimeForCurrentDay);
            pointsOverTimeForUser = pointsOverTimeRepository.findAllByUserIdAndDate(user.getId(), new CustomDate());
        }

        log.debug("Points at current day before adding points {}", pointsOverTimeForUser);
        Points newPointsValue = pointsOverTimeForUser.addPointsForCurrentDay(points);
        pointsOverTimeRepository.save(pointsOverTimeForUser);
        log.debug("Points at current day after adding {} points: {}", points.getPointsAsLong(), pointsOverTimeForUser);

        return newPointsValue.getPointsAsLong();
    }
}
