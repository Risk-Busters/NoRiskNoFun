package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.PointWithDate;
import com.riskbusters.norisknofun.domain.achievements.Achievement;

import java.io.Serializable;
import java.util.*;

/**
 * A DTO for the {@link com.riskbusters.norisknofun.domain.UserGamification} entity.
 */
public class UserGamificationDTO implements Serializable {

    private Long id;

    private Long userId;

    private Double activityScoreBasedOnPoints;

    private String userLogin;

    private Set<Achievement> userAchievements = new HashSet<>();

    private List<PointWithDate> pointsOverTime = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getActivityScoreBasedOnPoints() {
        return activityScoreBasedOnPoints;
    }

    public void setActivityScoreBasedOnPoints(Double activityScoreBasedOnPoints) {
        this.activityScoreBasedOnPoints = activityScoreBasedOnPoints;
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

    public Set<Achievement> getUserAchievements() {
        return userAchievements;
    }

    public void setUserAchievements(Set<Achievement> userAchievements) {
        this.userAchievements = userAchievements;
    }

    public List<PointWithDate> getPointsOverTime() {
        return pointsOverTime;
    }

    public void setPointsOverTime(List<PointWithDate> pointsOverTime) {
        this.pointsOverTime = pointsOverTime;
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
            ", pointsScore=" + getActivityScoreBasedOnPoints() +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", userAchievements='" + getUserAchievements() + "'" +
            ", pointsOverTime='" + getPointsOverTime() + "'" +
            "}";
    }
}
