package com.riskbusters.norisknofun.service.gamification;

import com.riskbusters.norisknofun.domain.PointsOverTime;
import com.riskbusters.norisknofun.domain.PointsWithDate;
import com.riskbusters.norisknofun.domain.User;
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
}
