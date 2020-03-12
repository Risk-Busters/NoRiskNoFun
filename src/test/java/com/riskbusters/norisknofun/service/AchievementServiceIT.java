package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.Points;
import com.riskbusters.norisknofun.domain.PointsOverTime;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.domain.achievements.Achievement;
import com.riskbusters.norisknofun.domain.achievements.ProjectMember;
import com.riskbusters.norisknofun.domain.achievements.RiskSage;
import com.riskbusters.norisknofun.repository.gamification.PointsOverTimeRepository;
import com.riskbusters.norisknofun.repository.gamification.UserGamificationRepository;
import com.riskbusters.norisknofun.repository.UserRepository;
import com.riskbusters.norisknofun.web.rest.UserResourceIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for {@link UserGamification pointsScore}.
 */
@SpringBootTest(classes = NoRiskNoFunApp.class)
@Transactional
public class AchievementServiceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGamificationRepository userGamificationRepository;

    @Autowired
    private PointsOverTimeRepository pointsOverTimeRepository;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private EntityManager em;

    private User user;

    private Long userId;

    Set<Achievement> achievements;

    private Set<Achievement> createAchievements() {
        achievements = new HashSet<>();
        achievements.add(new ProjectMember());
        achievementService.saveAchievements(achievements);
        return achievements;
    }

    public UserGamification createUserGamificationEntity(User user) {
        UserGamification userGamificationEntity = new UserGamification();
        userGamificationEntity.setUser(user);
        userGamificationEntity.setPointsScore(new Points(0L));
        Set<Achievement> achievements = createAchievements();
        userGamificationEntity.setUserAchievements(achievements);

        return userGamificationEntity;
    }

    @BeforeEach
    public void init() {
        pointsOverTimeRepository.deleteAll();
        userRepository.deleteAll();
        user = UserResourceIT.createEntity(em);
        userRepository.save(user);
        this.userId = user.getId();

        UserGamification userGamificationEntity = createUserGamificationEntity(user);
        userGamificationRepository.save(userGamificationEntity);
    }


    @Test
    @Transactional
    public void assertThatAchievmentsSetCorrectly() {
        achievementService.addAchievementsForUser(achievements, userId);

        assertEquals(achievements, userGamificationRepository.findByUserId(this.userId).getUserAchievements());
    }

    @Test
    @Transactional
    public void assertThatAchievmentsUpdatedCorrectly() {
        achievements.add(new RiskSage());
        achievementService.addAchievementsForUser(achievements, userId);

        assertEquals(achievements, userGamificationRepository.findByUserId(this.userId).getUserAchievements());
    }
}
