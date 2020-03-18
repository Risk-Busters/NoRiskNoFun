package com.riskbusters.norisknofun.domain;

import com.riskbusters.norisknofun.repository.UserRepository;
import com.riskbusters.norisknofun.repository.gamification.PointsOverTimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectActivityOverTimeCreator {

    private final Logger log = LoggerFactory.getLogger(ProjectActivityOverTimeCreator.class);

    private final UserRepository userRepository;
    private final PointsOverTimeRepository pointsOverTimeRepository;

    public ProjectActivityOverTimeCreator(UserRepository userRepository, PointsOverTimeRepository pointsOverTimeRepository) {
        this.userRepository = userRepository;
        this.pointsOverTimeRepository = pointsOverTimeRepository;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void createDatabaseEntryForProjectActivityPerDay() {
        log.debug("Create database entries for project activity per day:");
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            PointsOverTime pointsOneDay = new PointsOverTime(user, new CustomDate());
            pointsOverTimeRepository.save(pointsOneDay);
            log.debug("Points for one Day created object: {}", pointsOneDay);
        }
    }
}
