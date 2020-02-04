package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.Points;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.domain.achievements.Achievement;
import com.riskbusters.norisknofun.domain.achievements.ProjectMember;
import com.riskbusters.norisknofun.repository.UserGamificationRepository;
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
public class PointsServiceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGamificationRepository userGamificationRepository;

    @Autowired
    private PointsService pointsService;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private EntityManager em;

    private User user;

    private Long userId;

    private Set<Achievement> createAchievements() {
        Set<Achievement> achievements = new HashSet<>();
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
        userRepository.deleteAll();
        user = UserResourceIT.createEntity(em);
        userRepository.save(user);
        this.userId = user.getId();

        UserGamification userGamificationEntity = createUserGamificationEntity(user);
        userGamificationRepository.save(userGamificationEntity);
    }



    @Test
    @Transactional
    public void assertThatPointsAreIncreasedCorrectly() {
        pointsService.addPoints(new Points(42L), this.userId);

        assertEquals(Long.valueOf(42), userGamificationRepository.findByUserId(this.userId).getPointsScore().getPointsAsLong());
    }

    @Test
    @Transactional
    public void assertThatNegativePointValuesNotPossible() {
        pointsService.addPoints(new Points(-42L), this.userId);

        assertEquals(Long.valueOf(0), userGamificationRepository.findByUserId(this.userId).getPointsScore().getPointsAsLong());
    }

    @Test
    @Transactional
    public void assertThatMultipleIncreseProcessesAreCorrect() {
        pointsService.addPoints(new Points(2L), this.userId);
        assertEquals(Long.valueOf(2), userGamificationRepository.findByUserId(this.userId).getPointsScore().getPointsAsLong());
        pointsService.addPoints(new Points(6L), this.userId);
        assertEquals(Long.valueOf(8), userGamificationRepository.findByUserId(this.userId).getPointsScore().getPointsAsLong());
    }
}
