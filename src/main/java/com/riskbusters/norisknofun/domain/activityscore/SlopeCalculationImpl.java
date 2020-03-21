package com.riskbusters.norisknofun.domain.activityscore;

import com.riskbusters.norisknofun.domain.PointWithDate;

public class SlopeCalculationImpl implements SlopeCalculation {
    @Override
    public Double calculateSlopeBetweenTwoPoints(PointWithDate olderValue, PointWithDate youngerValue) {

        Long deltaX = 1L;
        Double deltaY = youngerValue.getPointsScore().doubleValue() - olderValue.getPointsScore();

        return deltaY/deltaX;
    }
}
