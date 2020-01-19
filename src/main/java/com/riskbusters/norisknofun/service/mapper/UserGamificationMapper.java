package com.riskbusters.norisknofun.service.mapper;

import com.riskbusters.norisknofun.domain.*;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGamification} and its DTO {@link UserGamificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class /*TODO , AchievementTypeMapper.class*/})
public interface UserGamificationMapper extends EntityMapper<UserGamificationDTO, UserGamification> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserGamificationDTO toDto(UserGamification userGamification);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "removeAchievementType", ignore = true)
    UserGamification toEntity(UserGamificationDTO userGamificationDTO);

    default UserGamification fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserGamification userGamification = new UserGamification();
        userGamification.setId(id);
        return userGamification;
    }
}
