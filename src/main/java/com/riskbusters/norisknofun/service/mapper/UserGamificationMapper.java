package com.riskbusters.norisknofun.service.mapper;

import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;

public class UserGamificationMapper {

    public UserGamificationDTO toUserGamificationDTO(UserGamification userGamification) {
        UserGamificationDTO userGamificationDTO = new UserGamificationDTO();
        userGamificationDTO.setId(userGamification.getId());
        userGamificationDTO.setPointsScore(userGamification.getPointsScore());
        userGamificationDTO.setUserAchievements((userGamification.getUserAchievements()));
        return userGamificationDTO;
    }

    public UserGamification userGamificationDTOtoUserGamification(UserGamificationDTO userGamificationDTO) {
        UserGamification userGamification = new UserGamification();
        userGamification.setId(userGamificationDTO.getId());
        userGamification.setPointsScore(userGamificationDTO.getPointsScore());
        userGamification.setUserAchievements(userGamificationDTO.getUserAchievements());
        return userGamification;
    }
}
