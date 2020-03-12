package com.riskbusters.norisknofun.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PointsWithDate implements Serializable {

    private Long pointsScore;

    private String date;

    public PointsWithDate(Long pointsScore, CustomDate date) {
        this.pointsScore = pointsScore;
        this.date = date.getCurrentDateFormatted();
    }

    public Long getPointsScore() {
        return pointsScore;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "PointsWithDate{" + "pointsScore=" + pointsScore + ", date='" + date + '\'' + '}';
    }
}
