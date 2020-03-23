package com.riskbusters.norisknofun.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PointsList {

    private final Logger log = LoggerFactory.getLogger(PointsList.class);

    private List<PointWithDate> finalCumulatedPointsByWeek;

    public PointsList(List<PointWithDate> allPointsOverTime) {
        calculateFinalCumulatedPointsByWeek(allPointsOverTime);
    }

    public PointsList() {
    }

    public void calculateFinalCumulatedPointsByWeek(List<PointWithDate> allPoints) {
        List<PointWithDate> allPointsOverTimeSorted = sortListNewest(allPoints);
        reverseList(allPointsOverTimeSorted);
        log.debug("ALL SORTED: {} ", allPointsOverTimeSorted);
        List<List<PointWithDate>> subListsPerWeek = sliceListIntoListsPerWeek(allPointsOverTimeSorted);
        log.debug("SUBLISTS: {} ", subListsPerWeek);
        List<PointWithDate> pointsOverTimePerWeek = sumUpListsPerWeek(subListsPerWeek);
        log.debug("SUM: {} ", pointsOverTimePerWeek);

        this.finalCumulatedPointsByWeek = sortListNewest(pointsOverTimePerWeek);
        log.debug("Grouped all points over time by week and calculate sum: {} ", this.finalCumulatedPointsByWeek);
    }

    private List<PointWithDate> sortListNewest(List<PointWithDate> allPointsOverTime) {
        PointWithDateComparator comparator = new PointWithDateComparator();
        allPointsOverTime.sort(comparator);
        return allPointsOverTime;
    }

    private void reverseList(List<PointWithDate> allPointsOverTime) {
        Collections.reverse(allPointsOverTime);
    }

    private List<List<PointWithDate>> sliceListIntoListsPerWeek(List<PointWithDate> allPointsOverTimeSorted) {
        int numberOfCumulatedDays = 7;
        return IntStream.range(0, (allPointsOverTimeSorted.size() + numberOfCumulatedDays - 1) / numberOfCumulatedDays)
            .mapToObj(i -> allPointsOverTimeSorted.subList(i * numberOfCumulatedDays, Math.min(numberOfCumulatedDays * (i + 1), allPointsOverTimeSorted.size())))
            .collect(Collectors.toList());
    }

    private List<PointWithDate> sumUpListsPerWeek(List<List<PointWithDate>> subListsPerWeek) {
        List<PointWithDate> pointsOverTimePerWeek = new ArrayList<>();
        for (List<PointWithDate> pointWithDates : subListsPerWeek) {
            Double cumulatedWeekValue = 0.0;
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(pointWithDates.get(0).getDate());
            } catch (ParseException e) {
                log.error("Parsing date failed", e);
            }
            CustomDate newestDateOfWeek = new CustomDate(date);
            for (PointWithDate pointWithDate : pointWithDates) {
                cumulatedWeekValue += pointWithDate.getPointsScore();
            }
            pointsOverTimePerWeek.add(new PointWithDate(cumulatedWeekValue, newestDateOfWeek));
        }
        return pointsOverTimePerWeek;
    }

    public List<PointWithDate> getFinalCumulatedPointsByWeek() {
        return finalCumulatedPointsByWeek;
    }
}
