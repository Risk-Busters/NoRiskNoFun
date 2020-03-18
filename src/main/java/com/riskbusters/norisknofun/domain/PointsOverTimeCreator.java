package com.riskbusters.norisknofun.domain;

import com.riskbusters.norisknofun.repository.UserRepository;
import com.riskbusters.norisknofun.repository.gamification.PointsOverTimeRepository;
import com.riskbusters.norisknofun.service.gamification.UserGamificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PointsOverTimeCreator {

    private final Logger log = LoggerFactory.getLogger(PointsOverTimeCreator.class);

    private final UserRepository userRepository;
    private final PointsOverTimeRepository pointsOverTimeRepository;
    private final UserGamificationService userGamificationService;

    public PointsOverTimeCreator(UserRepository userRepository, PointsOverTimeRepository pointsOverTimeRepository, UserGamificationService userGamificationService) {
        this.userRepository = userRepository;
        this.pointsOverTimeRepository = pointsOverTimeRepository;
        this.userGamificationService = userGamificationService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void createDatabaseEntryForPointsPerDayAndUser() {
        log.debug("Create database entries for the Points value for each day and each user:");
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            PointsOverTime pointsOneDay = new PointsOverTime(user, new CustomDate());
            pointsOverTimeRepository.save(pointsOneDay);
            log.debug("Points for one Day created object: {}", pointsOneDay);
            userGamificationService.calculateActivityScoreBasedOnPoints(user);
            log.debug("Calculated and stored current activityScoreBasedOnPoints");
        }
    }
}
