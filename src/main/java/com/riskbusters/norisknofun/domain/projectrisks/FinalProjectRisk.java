package com.riskbusters.norisknofun.domain.projectrisks;

import com.riskbusters.norisknofun.domain.ProjectRisks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("final")
public class FinalProjectRisk extends ProjectRisks {

    public FinalProjectRisk() {
        super();
    }
}
