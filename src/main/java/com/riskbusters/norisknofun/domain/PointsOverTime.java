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

    @Column
    @Embedded
    private CustomDate date;

    @Column
    private Points pointsAtThisDay;

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
        this.pointsAtThisDay.addPoints(points);
        return this.pointsAtThisDay;
    }

    @Override
    public String toString() {
        return "PointsOverTime{" + "id=" + id + ", userId=" + user.getId() + ", date=" + date.getCurrentDateFormatted() + ", pointsAtThisDay=" + pointsAtThisDay.getPointsAsLong() + '}';
    }
}
