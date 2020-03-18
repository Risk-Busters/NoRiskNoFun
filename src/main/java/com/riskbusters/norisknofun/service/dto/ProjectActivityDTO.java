package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.PointsWithDate;

import java.io.Serializable;
import java.util.List;

public class ProjectActivityDTO implements Serializable {

    private Double projectActivityBasedOnUserScore;
    private PointsWithDate[] projectActivitiesOverTime;

    public ProjectActivityDTO(Double projectActivityBasedOnUserScore, List<PointsWithDate> projectActivitiesOverTime) {
        this.projectActivityBasedOnUserScore = projectActivityBasedOnUserScore;

        this.projectActivitiesOverTime = new PointsWithDate[projectActivitiesOverTime.size()];

        for (int i = 0; i < projectActivitiesOverTime.size(); i++) {
            this.projectActivitiesOverTime[i] = projectActivitiesOverTime.get(i);
        }
    }

    public Double getProjectActivityBasedOnUserScore() {
        return projectActivityBasedOnUserScore;
    }

    public void setProjectActivityBasedOnUserScore(Double projectActivityBasedOnUserScore) {
        this.projectActivityBasedOnUserScore = projectActivityBasedOnUserScore;
    }

    public PointsWithDate[] getProjectActivitiesOverTime() {
        return projectActivitiesOverTime;
    }

    public void setProjectActivitiesOverTime(PointsWithDate[] projectActivitiesOverTime) {
        this.projectActivitiesOverTime = projectActivitiesOverTime;
    }

    @Override
    public String toString() {
        return "ProjectActivityDTO{" + "projectActivityToday=" + projectActivityBasedOnUserScore + ", projectActivitiesOverTime=" + projectActivitiesOverTime + '}';
    }
}
