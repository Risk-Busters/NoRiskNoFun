package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.PointsWithDate;

import java.io.Serializable;
import java.util.List;

public class ProjectActivityDTO implements Serializable {

    private Double projectActivityBasedOnUserScore;
    private List<PointsWithDate> projectActivitiesOverTime;

    public ProjectActivityDTO(Double projectActivityBasedOnUserScore, List<PointsWithDate> projectActivitiesOverTime) {
        this.projectActivityBasedOnUserScore = projectActivityBasedOnUserScore;
        this.projectActivitiesOverTime = projectActivitiesOverTime;
    }

    public Double getProjectActivityBasedOnUserScore() {
        return projectActivityBasedOnUserScore;
    }

    public void setProjectActivityBasedOnUserScore(Double projectActivityBasedOnUserScore) {
        this.projectActivityBasedOnUserScore = projectActivityBasedOnUserScore;
    }

    public List<PointsWithDate> getProjectActivitiesOverTime() {
        return projectActivitiesOverTime;
    }

    public void setProjectActivitiesOverTime(List<PointsWithDate> projectActivitiesOverTime) {
        this.projectActivitiesOverTime = projectActivitiesOverTime;
    }

    @Override
    public String toString() {
        return "ProjectActivityDTO{" + "projectActivityToday=" + projectActivityBasedOnUserScore + ", projectActivitiesOverTime=" + projectActivitiesOverTime + '}';
    }
}
