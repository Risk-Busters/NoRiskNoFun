package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.PointWithDate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ProjectActivityDTO implements Serializable {

    private Double projectActivityBasedOnUserScore;
    private PointWithDate[] projectActivitiesOverTime;

    public ProjectActivityDTO(Double projectActivityBasedOnUserScore, List<PointWithDate> projectActivitiesOverTime) {
        this.projectActivityBasedOnUserScore = projectActivityBasedOnUserScore;

        this.projectActivitiesOverTime = new PointWithDate[projectActivitiesOverTime.size()];

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

    public PointWithDate[] getProjectActivitiesOverTime() {
        return projectActivitiesOverTime;
    }

    public void setProjectActivitiesOverTime(PointWithDate[] projectActivitiesOverTime) {
        this.projectActivitiesOverTime = projectActivitiesOverTime;
    }

    @Override
    public String toString() {
        return "ProjectActivityDTO{" + "projectActivityToday=" + projectActivityBasedOnUserScore + ", projectActivitiesOverTime=" + Arrays.toString(projectActivitiesOverTime) + '}';
    }
}
