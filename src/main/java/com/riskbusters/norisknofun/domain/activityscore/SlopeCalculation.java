package com.riskbusters.norisknofun.domain.activityscore;

import com.riskbusters.norisknofun.domain.PointsWithDate;

public interface SlopeCalculation {

    Double calculateSlopeBetweenTwoPoints(PointsWithDate olderValue, PointsWithDate youngerValue);
}
