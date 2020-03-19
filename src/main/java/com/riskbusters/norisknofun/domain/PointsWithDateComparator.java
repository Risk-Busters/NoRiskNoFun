package com.riskbusters.norisknofun.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class PointsWithDateComparator implements Comparator<PointsWithDate> {

    private final Logger log = LoggerFactory.getLogger(PointsWithDateComparator.class);

    @Override
    public int compare(PointsWithDate o1, PointsWithDate o2) {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(o1.getDate());
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(o2.getDate());
            if (date1.before(date2)) {
                return -1;
            } else if (date1.after(date2)) {
                return 1;
            }
        } catch (ParseException e) {
            log.error("Getting date while comparing failed", e);
        }
        return 0;
    }

}
