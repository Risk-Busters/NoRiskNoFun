package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("riskOwner")
public class RiskOwner extends Achievement {

    public RiskOwner() {
        super();
        this.name = AchievementType.RISK_OWNER;
    }
}
