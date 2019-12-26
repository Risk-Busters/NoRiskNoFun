package com.riskbusters.norisknofun.domain.projectrisks;

import com.riskbusters.norisknofun.domain.ProjectRisks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("proposed")
public class ProposedProjectRisk extends ProjectRisks {

    public ProposedProjectRisk() {
        super();
    }
}
