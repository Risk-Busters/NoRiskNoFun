package com.riskbusters.norisknofun.domain;

import com.riskbusters.norisknofun.domain.achievements.Achievement;
import com.riskbusters.norisknofun.domain.achievements.RiskBuster;
import com.riskbusters.norisknofun.domain.achievements.RiskMaster;
import com.riskbusters.norisknofun.domain.achievements.RiskSage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserGamificationTest {

    private UserGamification userGamification;

    @BeforeEach
    void setup() {
        userGamification = new UserGamification();
    }

    @Test
    void testConstructor() {
        User user = new User();
        user.setId(1L);
        UserGamification userGamificationWithParams = new UserGamification(user, 4.0);
        assertEquals(Long.valueOf(1), userGamificationWithParams.getUser().getId());
        assertEquals(Double.valueOf(4.0), userGamificationWithParams.getActivityScoreBasedOnPoints());
    }

    @Test
    void setAndGetId() {
        userGamification.setId(2L);
        assertEquals(Long.valueOf(2L), userGamification.getId());
    }

    @Test
    void setAndGetActivityScoreBasedOnPoints() {
        userGamification.setActivityScoreBasedOnPoints(2.0);
        assertEquals(Double.valueOf(2.0), userGamification.getActivityScoreBasedOnPoints());
    }

    @Test
    void setAndGetUser() {
        User user = new User();
        user.setId(1L);
        userGamification.setUser(user);
        assertEquals(Long.valueOf(1L), userGamification.getUser().getId());
    }

    @Test
    void setAndGetUserAchievements() {
        Set<Achievement> achievements = new HashSet<>();
        achievements.add(new RiskBuster());
        achievements.add(new RiskSage());
        userGamification.setUserAchievements(achievements);
        assertEquals(achievements, userGamification.getUserAchievements());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(userGamification, userGamification);
    }

    @Test
    void testEqualsOtherObject() {
        UserGamification userGamification1 = new UserGamification();
        userGamification1.setId(1L);
        UserGamification userGamification2 = new UserGamification();
        userGamification2.setId(1L);
        assertEquals(userGamification1, userGamification2);
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(userGamification, new Object());
    }

    @Test
    void testToString() {
        User user = new User();
        user.setId(1L);
        Set<Achievement> achievements = new HashSet<>();
        achievements.add(new RiskBuster());
        userGamification.setId(3L);
        userGamification.setUser(user);
        userGamification.setActivityScoreBasedOnPoints(44.0);
        userGamification.setUserAchievements(achievements);

        String expected = "UserGamification{id=3userId=1, activityScoreBasedOnPoints=44.0, achievements=[Achievement{name=RISK_BUSTER}]}";
        assertEquals(expected, userGamification.toString());
    }

    @Test
    void testHashCode() {
        UserGamification userGamification1 = new UserGamification();
        UserGamification userGamification2 = new UserGamification();
        userGamification1.setId(1L);
        userGamification2.setId(1L);
        assertTrue(userGamification1.equals(userGamification2) && userGamification2.equals(userGamification1));
        assertEquals(userGamification1.hashCode(), userGamification2.hashCode());
    }
}
