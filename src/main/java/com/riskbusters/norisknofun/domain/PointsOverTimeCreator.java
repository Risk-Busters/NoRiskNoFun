package com.riskbusters.norisknofun.domain;

import com.riskbusters.norisknofun.repository.UserRepository;
import com.riskbusters.norisknofun.repository.gamification.PointsOverTimeRepository;
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

    public PointsOverTimeCreator(UserRepository userRepository, PointsOverTimeRepository pointsOverTimeRepository) {
        this.userRepository = userRepository;
        this.pointsOverTimeRepository = pointsOverTimeRepository;
    }

    // TODO: set it to 00:00 and test it
    @Scheduled(cron = "0 14 15 * * ?")
    public void createDatabaseEntryForPointsPerDayAndUser() {
        log.debug("Create database entries for the Points value for each day and each user:");
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            PointsOverTime pointsOneDay = new PointsOverTime();
            pointsOneDay.setUser(user);
            pointsOneDay.setDate(new CustomDate());
            pointsOneDay.setPointsAtThisDay(new Points(0L));
            pointsOverTimeRepository.save(pointsOneDay);
            log.debug("Points for one Day created object: {}", pointsOneDay);
        }
    }
}
