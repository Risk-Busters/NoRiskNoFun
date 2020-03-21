package com.riskbusters.norisknofun.web.rest.vm;

import com.riskbusters.norisknofun.domain.enumeration.ProbabilityType;
import com.riskbusters.norisknofun.domain.enumeration.SeverityType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class DiscussRiskVM {

    @NotNull
    @Enumerated(EnumType.STRING)
    private SeverityType projectSeverity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProbabilityType projectProbability;

    @NotNull
    private long projectRiskId;

    public SeverityType getProjectSeverity() {
        return projectSeverity;
    }

    public ProbabilityType getProjectProbability() {
        return projectProbability;
    }

    public long getProjectRiskId() {
        return projectRiskId;
    }
}
