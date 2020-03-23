package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointsOverTimeTest {

    private PointsOverTime pointsOverTimeWithContent;

    @BeforeEach
    void setUp() {
        pointsOverTimeWithContent = new PointsOverTime();
    }

    @Test
    void testConstructor() {
        User user = new User();
        user.setId(3L);
        CustomDate date = new CustomDate();
        PointsOverTime pointsOverTime = new PointsOverTime(user, date);
        assertEquals(Long.valueOf(3), pointsOverTime.getUser().getId());
        assertEquals(date.getDateFormatted(), pointsOverTime.getDate().getDateFormatted());
    }

    @Test
    void testGetSerialVersionUID() {
        assertEquals(1L, PointsOverTime.getSerialVersionUID());
    }

    @Test
    void testSetAndGetId() {
        pointsOverTimeWithContent.setId(2L);
        assertEquals(Long.valueOf(2), pointsOverTimeWithContent.getId());
    }

    @Test
    void testSetAndGetUser() {
        User user = new User();
        user.setId(3L);
        pointsOverTimeWithContent.setUser(user);
        assertEquals(Long.valueOf(3), pointsOverTimeWithContent.getUser().getId());
    }

    @Test
    void testSetAndGetDate() {
        Date todayExpected = java.sql.Date.valueOf(LocalDate.of(2020, 2, 4));
        CustomDate actual = new CustomDate(todayExpected);
        pointsOverTimeWithContent.setDate(actual);
        assertEquals(actual, pointsOverTimeWithContent.getDate());
    }

    @Test
    void testSetAndGetPointsAtThisDay() {
        pointsOverTimeWithContent.setPointsAtThisDay(new Points(4L));
        assertEquals(Long.valueOf(4), pointsOverTimeWithContent.getPointsAtThisDay().getPointsAsLong());
    }

    @Test
    void testAddPointsForCurrentDay() {
        pointsOverTimeWithContent.addPointsForCurrentDay(new Points(42L));
        pointsOverTimeWithContent.addPointsForCurrentDay(new Points(2L));
        assertEquals(Long.valueOf(44), pointsOverTimeWithContent.getPointsAtThisDay().getPointsAsLong());

    }

    @Test
    void testToString() {
        User user = new User();
        user.setId(4L);
        Date todayExpected = java.sql.Date.valueOf(LocalDate.of(2020, 2, 4));
        CustomDate actual = new CustomDate(todayExpected);
        pointsOverTimeWithContent.setId(1L);
        pointsOverTimeWithContent.setUser(user);
        pointsOverTimeWithContent.setDate(actual);
        pointsOverTimeWithContent.setPointsAtThisDay(new Points(500L));

        String expected = "PointsOverTime{id=1, userId=4, date=2020-02-04, pointsAtThisDay=500}";
        assertEquals(expected, pointsOverTimeWithContent.toString());
    }
}
