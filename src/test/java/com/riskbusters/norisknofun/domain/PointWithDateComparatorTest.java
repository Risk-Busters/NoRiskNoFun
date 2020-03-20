package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointWithDateComparatorTest {

    private PointWithDate youngerPoint;
    private PointWithDate olderPoint;
    PointWithDateComparator comparator;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date yesterdayDate = calendar.getTime();

        youngerPoint = new PointWithDate(2.0, new CustomDate());
        olderPoint = new PointWithDate(4.0, new CustomDate(yesterdayDate));
        comparator = new PointWithDateComparator();
    }

    @Test
    void compareYoungerPointsIsEarlier() {
        assertEquals(1, comparator.compare(youngerPoint, olderPoint));
    }

    @Test
    void compareOlderPointIsOlder() {
        assertEquals(-1, comparator.compare(olderPoint, youngerPoint));
    }

    @Test
    void compareDatesEqual() {
        assertEquals(0, comparator.compare(youngerPoint, youngerPoint));
    }
}
