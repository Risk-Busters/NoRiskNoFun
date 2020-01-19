package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.achievements.Achievement;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.riskbusters.norisknofun.domain.UserGamification} entity.
 */
public class UserGamificationDTO implements Serializable {

    private Long id;

    private Long pointsScore;


    private Long userId;

    private String userLogin;

    private Set<Achievement> userAchievementsTypes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPointsScore() {
        return pointsScore;
    }

    public void setPointsScore(Long pointsScore) {
        this.pointsScore = pointsScore;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Set<Achievement> getUserAchievementsTypes() {
        return userAchievementsTypes;
    }

    public void setUserAchievementsTypes(Set<Achievement> userAchievementsTypes) {
        this.userAchievementsTypes = userAchievementsTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserGamificationDTO userGamificationDTO = (UserGamificationDTO) o;
        if (userGamificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userGamificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserGamificationDTO{" +
            "id=" + getId() +
            ", pointsScore=" + getPointsScore() +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
