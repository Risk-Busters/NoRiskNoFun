package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.Points;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.repository.gamification.UserGamificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserGamification pointsScore}.
 */
@Service
@Transactional
public class PointsService {

    private final Logger log = LoggerFactory.getLogger(PointsService.class);
    private final UserGamificationRepository userGamificationRepository;

    public PointsService(UserGamificationRepository userGamificationRepository) {
        this.userGamificationRepository = userGamificationRepository;
    }

    /**
     * Add points for a user
     *
     * @param pointsToAdd amount of points to increase user pointsScore
     * @param userId      add points for this user
     */
    public void addPointsForUser(Points pointsToAdd, Long userId) {
        log.debug("Add {} to the points score of user with id {}", pointsToAdd, userId);
        UserGamification userGamification = userGamificationRepository.findByUserId(userId);
        userGamification.addPoints(pointsToAdd);
        userGamificationRepository.save(userGamification);
    }
}
