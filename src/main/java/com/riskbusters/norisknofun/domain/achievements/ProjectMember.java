package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("projectMember")
public class ProjectMember extends Achievement {

    public ProjectMember() {
        super();
        this.name = AchievementType.PROJECT_MEMBER;
    }
}
