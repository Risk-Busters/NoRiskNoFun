package com.riskbusters.norisknofun.domain.enumeration;

public enum RiskDiscussionState  {
    PROPOSED("proposed"),
    DISCUSSION("toBeDiscussed"),
    FINAL("final");

    private String state;

    RiskDiscussionState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
