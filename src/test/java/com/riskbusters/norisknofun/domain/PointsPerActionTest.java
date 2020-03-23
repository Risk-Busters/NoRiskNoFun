package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointsPerActionTest {

    @Test
    public void testCheckValues() {
        assertEquals(Long.valueOf(2), PointsPerAction.PROPOSED_A_RISK.getPointsAsLong());
        assertEquals(Long.valueOf(1), PointsPerAction.REVIEWED_A_RISK.getPointsAsLong());
        assertEquals(Long.valueOf(4), PointsPerAction.BE_PERSON_IN_CHARGE.getPointsAsLong());
    }
}
