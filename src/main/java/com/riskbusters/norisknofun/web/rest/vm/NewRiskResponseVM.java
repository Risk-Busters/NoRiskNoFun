package com.riskbusters.norisknofun.web.rest.vm;

import com.riskbusters.norisknofun.domain.RiskResponse;

import javax.validation.constraints.NotNull;

/**
 * View Model object for storing simple risk response and the corresponding project id
 */
public class NewRiskResponseVM {

    @NotNull
    private RiskResponse riskResponse;

    private Long projectRiskId;

    public RiskResponse getRiskResponse() {
        return riskResponse;
    }

    public Long getProjectRiskId() {
        return projectRiskId;
    }
}
