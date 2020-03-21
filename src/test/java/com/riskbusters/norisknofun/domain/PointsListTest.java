package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class PointsListTest {

    private PointsList pointsList;
    private List<PointWithDate> allPointsOverTime;

    @BeforeEach
    void setUp() {
        allPointsOverTime = new ArrayList<>();

        List<LocalDate> dates = generateDates();
        fillPointWithDateList(dates);
    }

    private List<LocalDate> generateDates() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 3, 30);
        return startDate.datesUntil(endDate).collect(Collectors.toList());
    }

    private void fillPointWithDateList(List<LocalDate> dates) {
        for (int i = 0; i < dates.size(); i++) {
            Date date = java.sql.Date.valueOf(dates.get(i));
            PointWithDate point = new PointWithDate((double) i, new CustomDate(date));
            allPointsOverTime.add(point);
        }
    }

    @Test
    void testCalculationOfFinalCumulatedPointsByWeek() {
        pointsList = new PointsList(allPointsOverTime);

        List<PointWithDate> expected = new ArrayList<>();
        expected.add(new PointWithDate(595.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 3, 29)))));
        expected.add(new PointWithDate(546.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 3, 22)))));
        expected.add(new PointWithDate(497.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 3, 15)))));
        expected.add(new PointWithDate(448.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 3, 8)))));
        expected.add(new PointWithDate(399.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 3, 1)))));
        expected.add(new PointWithDate(350.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 2, 23)))));
        expected.add(new PointWithDate(301.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 2, 16)))));
        expected.add(new PointWithDate(252.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 2, 9)))));
        expected.add(new PointWithDate(203.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 2, 2)))));
        expected.add(new PointWithDate(154.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 1, 26)))));
        expected.add(new PointWithDate(105.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 1, 19)))));
        expected.add(new PointWithDate(56.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 1, 12)))));
        expected.add(new PointWithDate(10.0, new CustomDate(java.sql.Date.valueOf(LocalDate.of(2020, 1, 5)))));

        List<PointWithDate> result = pointsList.getFinalCumulatedPointsByWeek();
        result.sort(new PointWithDateComparator());
        expected.sort(new PointWithDateComparator());
        assertIterableEquals(expected, result);
    }
}
