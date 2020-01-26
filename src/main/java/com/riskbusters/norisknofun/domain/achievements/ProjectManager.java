package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("projectManager")
public class ProjectManager extends Achievement {

    public ProjectManager() {
        super();
        this.name = AchievementType.PROJECT_MANAGER;
    }
}
