package com.riskbusters.norisknofun.web.rest.vm;

/**
 * View Model object for storing simple risk title and description
 */
public class ProposeRiskVM {
    public String title;

    public String description;

    public long projectId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
