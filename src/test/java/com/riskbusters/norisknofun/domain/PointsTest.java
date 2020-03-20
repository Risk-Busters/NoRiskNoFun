package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointsTest {

    @Test
    void testPositiveInitializationWithPositiveValue() {
        Points pointsScore = new Points(2L);
        assertEquals(Long.valueOf(2L), pointsScore.getPointsAsLong());
    }

    @Test
    void testNegativeInitializationWithZero() {
        Points pointsScore = new Points(0L);
        assertEquals(Long.valueOf(0L), pointsScore.getPointsAsLong());
    }

    @Test
    void testNegativeInitializationWithNegativeValue() {
        Points pointsScore = new Points(-2L);
        assertEquals(Long.valueOf(0L), pointsScore.getPointsAsLong());
    }

    @Test
    void testAddPoints() {
        Points pointsScore = new Points(0L);
        assertEquals(Long.valueOf(2L), pointsScore.addPoints(new Points(2L)));
    }
}
