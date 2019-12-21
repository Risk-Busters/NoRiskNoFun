package com.riskbusters.norisknofun.domain.projectrisks;

import com.riskbusters.norisknofun.domain.ProjectRisks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ToBeDiscussed")
public class ToBeDiscussedProjectRisk extends ProjectRisks {

    public ToBeDiscussedProjectRisk() {
        super();
    }
}
