package com.riskbusters.norisknofun.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PointsWithDate implements Serializable {

    private Double pointsScore;

    private String date;

    public PointsWithDate(Double pointsScore, CustomDate date) {
        this.pointsScore = pointsScore;
        this.date = date.getDateFormatted();
    }

    public PointsWithDate() {
    }

    public Double getPointsScore() {
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
