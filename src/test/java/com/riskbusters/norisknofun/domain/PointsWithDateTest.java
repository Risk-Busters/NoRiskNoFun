package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PointsWithDateTest {

    private PointsWithDate pointsWithDate;

    @BeforeEach
    void setUp() {
        pointsWithDate = new PointsWithDate(42.0, new CustomDate());
    }

    @Test
    void getPointsScore() {
        assertEquals(Long.valueOf(42), pointsWithDate.getPointsScore());
    }

    @Test
    void getDate() {
        String expected = LocalDate.now().toString();
        assertEquals(expected, pointsWithDate.getDate());
    }

    @Test
    void testToString() {
        String expected = "PointsWithDate{pointsScore=42, date='" + LocalDate.now().toString() + "'}";
        assertEquals(expected, pointsWithDate.toString());
    }
}
