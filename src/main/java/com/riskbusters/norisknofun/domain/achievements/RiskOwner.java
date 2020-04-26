package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("riskOwner")
public class RiskOwner extends Achievement {

    public RiskOwner() {
        super();
        this.name = AchievementType.RISK_OWNER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskOwner)) {
            return false;
        }
        return name != null && name.equals(((RiskOwner) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "owner";
    }
}
