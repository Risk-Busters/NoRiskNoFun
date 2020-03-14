package com.riskbusters.norisknofun.domain;

public final class PointsPerAction {

    public static final Points PROPOSED_A_RISK = new Points(2L);
    public static final Points REVIEWED_A_RISK = new Points(1L);
    public static final Points BE_PERSON_IN_CHARGE = new Points(1L);

    private PointsPerAction() {
        throw new IllegalStateException("Central dict class");
    }
}
