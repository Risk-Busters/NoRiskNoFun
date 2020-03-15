package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.PointsWithDate;

import java.io.Serializable;
import java.util.List;

public class ProjectActivityDTO implements Serializable {

    private Long projectActivityToday;
    private List<PointsWithDate> projectActivitiesOverTime;

    public ProjectActivityDTO(Long projectActivityToday, List<PointsWithDate> projectActivitiesOverTime) {
        this.projectActivityToday = projectActivityToday;
        this.projectActivitiesOverTime = projectActivitiesOverTime;
    }

    public Long getProjectActivityToday() {
        return projectActivityToday;
    }

    public void setProjectActivityToday(Long projectActivityToday) {
        this.projectActivityToday = projectActivityToday;
    }

    public List<PointsWithDate> getProjectActivitiesOverTime() {
        return projectActivitiesOverTime;
    }

    public void setProjectActivitiesOverTime(List<PointsWithDate> projectActivitiesOverTime) {
        this.projectActivitiesOverTime = projectActivitiesOverTime;
    }

    @Override
    public String toString() {
        return "ProjectActivityDTO{" + "projectActivityToday=" + projectActivityToday + ", projectActivitiesOverTime=" + projectActivitiesOverTime + '}';
    }
}
