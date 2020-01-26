package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.achievements.Achievement;
import com.riskbusters.norisknofun.domain.achievements.ProjectMember;
import com.riskbusters.norisknofun.domain.achievements.RiskBuster;
import com.riskbusters.norisknofun.domain.achievements.RiskMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserGamificationDTOTest {

    private UserGamificationDTO userGamificationDTO;

    private Set<Achievement> userAchievements = new HashSet<>();

    @BeforeEach
    void setUp() {
        this.userGamificationDTO = new UserGamificationDTO();
        userGamificationDTO.setUserId(1L);
        userGamificationDTO.setPointsScore(42L);
        userGamificationDTO.setId(7L);
        userGamificationDTO.setUserLogin("admin");
        userAchievements.add(new ProjectMember());
        userGamificationDTO.setUserAchievements(userAchievements);
    }

    @Test
    void getId() {
        assertEquals(7L, (long) userGamificationDTO.getId());
    }

    @Test
    void setId() {
        userGamificationDTO.setId(77L);
        assertEquals(77L, (long) userGamificationDTO.getId());
    }

    @Test
    void getPointsScore() {
        assertEquals(42L, (long) userGamificationDTO.getPointsScore());
    }

    @Test
    void setPointsScore() {
        userGamificationDTO.setPointsScore(4242L);
        assertEquals(4242L, (long) userGamificationDTO.getPointsScore());
    }

    @Test
    void getUserId() {
        assertEquals(1L, (long) userGamificationDTO.getUserId());
    }

    @Test
    void setUserId() {
        userGamificationDTO.setUserId(11L);
        assertEquals(11L, (long) userGamificationDTO.getUserId());
    }

    @Test
    void getUserLogin() {
        assertEquals("admin", userGamificationDTO.getUserLogin());
    }

    @Test
    void setUserLogin() {
        userGamificationDTO.setUserLogin("user");
        assertEquals("user", userGamificationDTO.getUserLogin());
    }

    @Test
    void getUserAchievements() {
        assertEquals(userAchievements, userGamificationDTO.getUserAchievements());
    }

    @Test
    void setUserAchievements() {
        userGamificationDTO.setUserAchievements(new HashSet<>());
        Set<Achievement> userAchievements = new HashSet<>();
        userAchievements.add(new RiskMaster());
        userAchievements.add(new RiskBuster());
        userGamificationDTO.setUserAchievements(userAchievements);
        assertEquals(userAchievements, userGamificationDTO.getUserAchievements());
    }

    @Test
    void testEquals() {
        UserGamificationDTO userGamificationDTO = new UserGamificationDTO();
        UserGamificationDTO userGamificationDTO2 = new UserGamificationDTO();
        assertTrue(userGamificationDTO.equals(userGamificationDTO));
        assertFalse(userGamificationDTO.equals(null));
        assertFalse(userGamificationDTO.equals(userGamificationDTO2));
        userGamificationDTO.setId(1L);
        userGamificationDTO2.setId(2L);
        assertFalse(userGamificationDTO.equals(userGamificationDTO2));
    }

    @Test
    void testHashCode() {
        UserGamificationDTO userGamificationDTO = new UserGamificationDTO();
        assertEquals(userGamificationDTO.hashCode(), userGamificationDTO.hashCode());
    }
}
