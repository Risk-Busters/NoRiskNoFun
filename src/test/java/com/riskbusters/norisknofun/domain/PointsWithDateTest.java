package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointsWithDateTest {

    private PointsWithDate pointsWithDate;

    @BeforeEach
    void setUp() {
        pointsWithDate = new PointsWithDate(42.0, new CustomDate());
    }

    @Test
    void getPointsScore() {
        assertEquals(Double.valueOf(42.0), pointsWithDate.getPointsScore());
    }

    @Test
    void getDate() {
        String expected = LocalDate.now().toString();
        assertEquals(expected, pointsWithDate.getDate());
    }

    @Test
    void testToString() {
        String expected = "PointsWithDate{pointsScore=42.0, date='" + LocalDate.now().toString() + "'}";
        assertEquals(expected, pointsWithDate.toString());
    }
}
