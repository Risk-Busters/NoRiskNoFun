package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("riskSage")
public class RiskSage extends Achievement {

    public RiskSage() {
        super();
        this.name = AchievementType.RISK_SAGE;
    }
}
