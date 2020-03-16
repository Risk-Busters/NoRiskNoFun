package com.riskbusters.norisknofun.domain.activityscore;

import com.riskbusters.norisknofun.domain.PointsWithDate;

public class SlopeCalculationImpl implements SlopeCalculation {
    @Override
    public Double calculateSlopeBetweenTwoPoints(PointsWithDate olderValue, PointsWithDate youngerValue) {

        Long deltaX = 1L;
        Double deltaY = youngerValue.getPointsScore().doubleValue() - olderValue.getPointsScore();

        return deltaY/deltaX;
    }
}
