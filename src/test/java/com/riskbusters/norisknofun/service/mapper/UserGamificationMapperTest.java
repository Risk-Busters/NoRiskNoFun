package com.riskbusters.norisknofun.service.mapper;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.PointWithDate;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.domain.achievements.Achievement;
import com.riskbusters.norisknofun.domain.achievements.RiskBuster;
import com.riskbusters.norisknofun.repository.UserRepository;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserGamificationMapperTest {

    private UserGamificationMapper mapper;
    private UserGamification userGamification;
    private UserGamificationDTO userGamificationDTO;
    private User user;

    private UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);

    @BeforeEach
    void init() {
        mapper = new UserGamificationMapper(userRepositoryMock);

        userGamification = new UserGamification();
        userGamificationDTO = new UserGamificationDTO();

        user = new User();
        user.setId(1L);
        Set<Achievement> achievements = new HashSet<>();
        achievements.add(new RiskBuster());
        userGamification.setId(3L);
        userGamification.setUser(user);
        userGamification.setActivityScoreBasedOnPoints(44.0);
        userGamification.setUserAchievements(achievements);

        userGamificationDTO.setId(3L);
        userGamificationDTO.setUserId(user.getId());
        userGamificationDTO.setActivityScoreBasedOnPoints(44.0);
        userGamificationDTO.setUserAchievements(achievements);
    }

    @Test
    void toUserGamificationDTO() {
        assertEquals(userGamificationDTO, mapper.toUserGamificationDTO(userGamification));
    }

    @Test
    void toUserGamificationDTOWithPointsOverTime() {
        List<PointWithDate> pointsOverTime = new ArrayList<>();
        pointsOverTime.add(new PointWithDate(2.0, new CustomDate()));

        userGamificationDTO.setPointsOverTime(pointsOverTime);
        assertEquals(userGamificationDTO, mapper.toUserGamificationDTO(userGamification, pointsOverTime));
    }

    @Test
    void userGamificationDTOtoUserGamification() {
        Mockito.when(userRepositoryMock.getById(userGamificationDTO.getUserId())).thenReturn(user);

        assertEquals(userGamification, mapper.userGamificationDTOtoUserGamification(userGamificationDTO));
    }
}
