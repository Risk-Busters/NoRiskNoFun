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
        assertFalse(pointWithDate.equals(null));
    }

    @Test
    void testHashCode() {
        PointWithDate point1 = new PointWithDate();
        PointWithDate point2 = new PointWithDate();
        assertTrue(point1.equals(point2) && point2.equals(point1));
        assertEquals(point1.hashCode(), point2.hashCode());
    }

    @Test
    void testToString() {
        String expected = "PointWithDate{date='" + LocalDate.now().toString() + ", pointsScore=42.0" + "'}";
        assertEquals(expected, pointWithDate.toString());
    }
}
