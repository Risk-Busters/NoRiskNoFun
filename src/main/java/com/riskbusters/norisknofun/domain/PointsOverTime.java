package com.riskbusters.norisknofun.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Table holding all points by day and user.
 */
@Entity
@Table(name = "user_gamification_points_over_time")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PointsOverTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn
    private User user;

    @Column(name = "date")
    @Embedded
    private CustomDate date;

    @Column(name = "points_at_this_day")
    private Points pointsAtThisDay;

    public PointsOverTime(User user, CustomDate date) {
        this.user = user;
        this.date = date;
        this.pointsAtThisDay = new Points(0L);
    }

    public PointsOverTime() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CustomDate getDate() {
        return date;
    }

    public void setDate(CustomDate date) {
        this.date = date;
    }

    public Points getPointsAtThisDay() {
        return pointsAtThisDay;
    }

    public void setPointsAtThisDay(Points pointsAtThisDay) {
        this.pointsAtThisDay = pointsAtThisDay;
    }

    public Points addPointsForCurrentDay(Points points) {
        if (this.pointsAtThisDay == null) {
            this.pointsAtThisDay = points;
        } else {
            this.pointsAtThisDay.addPoints(points);
        }
        return this.pointsAtThisDay;
    }

    @Override
    public String toString() {
        return "PointsOverTime{" + "id=" + id + ", userId=" + user.getId() + ", date=" + date.getDateFormatted() + ", pointsAtThisDay=" + pointsAtThisDay.getPointsAsLong() + '}';
    }
}
