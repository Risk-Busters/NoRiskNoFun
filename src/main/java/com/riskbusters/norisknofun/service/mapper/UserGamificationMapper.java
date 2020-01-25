package com.riskbusters.norisknofun.service.mapper;

import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;

public class UserGamificationMapper {

    public UserGamificationDTO toUserGamificationDTO(UserGamification userGamification) {
        UserGamificationDTO userGamificationDTO = new UserGamificationDTO();
        userGamificationDTO.setId(userGamification.getId());
        // TODO: verify if firstname is correct here
        // userGamificationDTO.setUserLogin(userGamification.getUser().getFirstName());
        userGamificationDTO.setPointsScore(userGamification.getPointsScore());
        userGamificationDTO.setUserAchievements((userGamification.getUserAchievements()));
        // TODO: userGamificationDTO.setUserId(userGamification.getUser().getId());
        return userGamificationDTO;
    }

    public UserGamification userGamificationDTOtoUserGamification(UserGamificationDTO userGamificationDTO) {
        UserGamification userGamification = new UserGamification();
        userGamification.setId(userGamification.getId());
        userGamification.setUser(userGamification.getUser());
        userGamification.setPointsScore(userGamification.getPointsScore());
        userGamification.setUserAchievements(userGamification.getUserAchievements());
        return userGamification;
    }
}
