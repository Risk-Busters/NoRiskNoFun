package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("projectManager")
public class ProjectManager extends Achievement {

    public ProjectManager() {
        super();
        this.name = AchievementType.PROJECT_MANAGER;
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

    @Override
    public String toString() {
        return "manager";
    }
}
