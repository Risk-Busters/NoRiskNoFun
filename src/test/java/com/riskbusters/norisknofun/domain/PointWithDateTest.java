package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PointWithDateTest {

    private PointWithDate pointWithDate;

    @BeforeEach
    void setUp() {
        pointWithDate = new PointWithDate(42.0, new CustomDate());
    }

    @Test
    void getPointsScore() {
        assertEquals(Double.valueOf(42.0), pointWithDate.getPointsScore());
    }

    @Test
    void getDate() {
        String expected = LocalDate.now().toString();
        assertEquals(expected, pointWithDate.getDate());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(pointWithDate, pointWithDate);
    }

    @Test
    void testEqualsOtherObject() {
        assertNotEquals(pointWithDate, new Object());
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(null, pointWithDate);
    }

    @Test
    void testToString() {
        String expected = "PointWithDate{date='" + LocalDate.now().toString() + ", pointsScore=42.0" + "'}";
        assertEquals(expected, pointWithDate.toString());
    }
}
