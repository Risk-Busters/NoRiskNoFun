package com.riskbusters.norisknofun.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Points implements Serializable {

    private Long pointsScore;

    public Points() {
    }

    public Points(Long pointsScore) {
        if (pointsScore >= 0) {
            this.pointsScore = pointsScore;
        } else {
            this.pointsScore = 0L;
        }
    }

    public Long getPointsAsLong() {
        return pointsScore;
    }

    public Long addPoints(Points pointsScore) {
        this.pointsScore += pointsScore.getPointsAsLong();
        return this.pointsScore;
    }
}
