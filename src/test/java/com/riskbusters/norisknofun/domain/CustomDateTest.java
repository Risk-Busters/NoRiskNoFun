package com.riskbusters.norisknofun.domain;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomDateTest {

    @Test
    void testDefaultDate() {
        CustomDate todayActual = new CustomDate();

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String todayExpected = form.format(new Date());

        assertEquals(todayExpected, todayActual.getDateFormatted());
    }

    @Test
    void testSpecificDate() {
        Date specificDate = java.sql.Date.valueOf(LocalDate.of(2020, 2, 4));

        CustomDate actual = new CustomDate(specificDate);
        assertEquals("2020-02-04", actual.getDateFormatted());
    }

    @Test
    void getDateFormat() {
        CustomDate date = new CustomDate();
        assertEquals(new SimpleDateFormat("yyyy-MM-dd"), date.getDateFormat());
    }

    @Test
    void testToString() {
        Date todayExpected = java.sql.Date.valueOf(LocalDate.of(2020, 2, 4));

        CustomDate actual = new CustomDate(todayExpected);
        assertEquals("CustomDate{localDate=2020-02-04}", actual.toString());
    }
}
