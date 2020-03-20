package com.riskbusters.norisknofun.domain.activityscore;

import com.riskbusters.norisknofun.domain.PointWithDate;

public interface SlopeCalculation {

    Double calculateSlopeBetweenTwoPoints(PointWithDate olderValue, PointWithDate youngerValue);
}
