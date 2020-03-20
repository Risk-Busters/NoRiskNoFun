package com.riskbusters.norisknofun.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PointWithDate implements Serializable {

    private String date;

    private Double pointsScore;

    public PointWithDate(Double pointsScore, CustomDate date) {
        this.pointsScore = pointsScore;
        this.date = date.getDateFormatted();
    }

    public PointWithDate() {
    }

    public Double getPointsScore() {
        return pointsScore;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "PointWithDate{" + "date='" + date + ", pointsScore=" + pointsScore + '\'' + '}';
    }

    @Override
    public boolean equals(Object obj) {
        // self check
        if (this == obj)
            return true;
        // null check
        if (obj == null)
            return false;
        // type check and cast
        if (getClass() != obj.getClass())
            return false;
        PointWithDate pointsList = (PointWithDate) obj;
        // field comparison
        return Objects.equals(this.date, pointsList.date) && Objects.equals(this.pointsScore, pointsList.pointsScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, pointsScore);
    }
}
