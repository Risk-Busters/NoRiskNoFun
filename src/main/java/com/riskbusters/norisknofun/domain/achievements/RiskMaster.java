package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("riskMaster")
public class RiskMaster extends Achievement {

    public RiskMaster() {
        super();
        this.name = AchievementType.RISK_MASTER;
    }
}
