package com.riskbusters.norisknofun.service.mapper;

import com.riskbusters.norisknofun.domain.PointWithDate;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.repository.UserRepository;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGamificationMapper {

    private UserRepository userRepository;

    public UserGamificationMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserGamificationDTO toUserGamificationDTO(UserGamification userGamification) {
        UserGamificationDTO userGamificationDTO = new UserGamificationDTO();
        userGamificationDTO.setId(userGamification.getId());
        userGamificationDTO.setActivityScoreBasedOnPoints(userGamification.getActivityScoreBasedOnPoints());
        userGamificationDTO.setUserAchievements((userGamification.getUserAchievements()));
        return userGamificationDTO;
    }

    public UserGamificationDTO toUserGamificationDTO(UserGamification userGamification, List<PointWithDate> pointsOverTime) {
        UserGamificationDTO userGamificationDTO = new UserGamificationDTO();
        userGamificationDTO.setId(userGamification.getId());
        userGamificationDTO.setActivityScoreBasedOnPoints(userGamification.getActivityScoreBasedOnPoints());
        userGamificationDTO.setUserAchievements((userGamification.getUserAchievements()));
        userGamificationDTO.setPointsOverTime(pointsOverTime);
        return userGamificationDTO;
    }

    public UserGamification userGamificationDTOtoUserGamification(UserGamificationDTO userGamificationDTO) {
        UserGamification userGamification = new UserGamification();
        userGamification.setId(userGamificationDTO.getId());
        userGamification.setUser(userRepository.getById(userGamificationDTO.getUserId()));
        userGamification.setActivityScoreBasedOnPoints(userGamificationDTO.getActivityScoreBasedOnPoints());
        userGamification.setUserAchievements(userGamificationDTO.getUserAchievements());
        return userGamification;
    }
}
