package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("riskBuster")
public class RiskBuster extends Achievement {

    public RiskBuster() {
        super();
        this.name = AchievementType.RISK_BUSTER;
    }
}
