package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("projectMember")
public class ProjectMember extends Achievement {

    public ProjectMember() {
        super();
        this.name = AchievementType.PROJECT_MEMBER;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectManager)) {
            return false;
        }
        return name != null && name.equals(((ProjectManager) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
