package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("riskMaster")
public class RiskMaster extends Achievement {

    public RiskMaster() {
        super();
        this.name = AchievementType.RISK_MASTER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskMaster)) {
            return false;
        }
        return name != null && name.equals(((RiskMaster) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "master";
    }
}
