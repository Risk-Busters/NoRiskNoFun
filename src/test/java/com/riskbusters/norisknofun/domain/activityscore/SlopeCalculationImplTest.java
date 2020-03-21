package com.riskbusters.norisknofun.domain.activityscore;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.PointWithDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlopeCalculationImplTest {

    private PointWithDate olderPoint;
    private PointWithDate youngerPoint;

    @BeforeEach
    void setUp() {
        Date olderDate = java.sql.Date.valueOf(LocalDate.of(2020, 1, 1));
        Date youngerDate = java.sql.Date.valueOf(LocalDate.of(2020, 1, 2));

        olderPoint = new PointWithDate(1.0, new CustomDate(olderDate));
        youngerPoint = new PointWithDate(2.0, new CustomDate(youngerDate));

    }

    @Test
    void calculateSlopeBetweenTwoPoints() {
        SlopeCalculation slopeCalculation = new SlopeCalculationImpl();
        Double result = slopeCalculation.calculateSlopeBetweenTwoPoints(olderPoint, youngerPoint);
        assertEquals(Double.valueOf(1.0), result);
    }
}
