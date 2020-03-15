package com.riskbusters.norisknofun.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Table holding all the activity score for each project and each day.
 */
@Entity
@Table(name = "project_activity_over_time")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectActivityOverTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn
    private Project project;

    @Column
    @Embedded
    private CustomDate date;

    @Column
    private Points projectActivityAtThisDay;

    public ProjectActivityOverTime(Project project) {
        this.project = project;
        this.date = new CustomDate();
        this.projectActivityAtThisDay = new Points(0L);
    }

    public ProjectActivityOverTime() {
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public CustomDate getDate() {
        return date;
    }

    public void setDate(CustomDate date) {
        this.date = date;
    }

    public Points getProjectActivityAtThisDay() {
        return projectActivityAtThisDay;
    }

    public void setProjectActivityAtThisDay(Points pointsAtThisDay) {
        this.projectActivityAtThisDay = pointsAtThisDay;
    }

    @Override
    public String toString() {
        return "ProjectActivity{" + "id=" + id + ", projectId=" + project.getId() + ", date=" + date.getCurrentDateFormatted() + ", activityAtThisDay=" + projectActivityAtThisDay.getPointsAsLong() + '}';
    }
}
