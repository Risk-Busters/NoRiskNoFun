package com.riskbusters.norisknofun.domain;

public class Points {

    private Long pointsScore;

    public Points(Long pointsScore) {
        if(pointsScore >= 0) {
            this.pointsScore = pointsScore;
        } else {
            this.pointsScore = 0L;
        }
    }

    public Long getPointsAsLong() {
        return pointsScore;
    }

    public void addPoints(Points pointsScore) {
        this.pointsScore += pointsScore.getPointsAsLong();
    }
}
