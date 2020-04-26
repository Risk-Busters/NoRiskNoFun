package com.riskbusters.norisknofun.domain;

import com.riskbusters.norisknofun.domain.achievements.Achievement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A UserGamification.
 */
@Entity
@Table(name = "user_gamification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserGamification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne(cascade=CascadeType.REMOVE)
    @JoinColumn(unique = true)
    private User user;

    @Column(name = "activity_score_based_on_points")
    private Double activityScoreBasedOnPoints;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_gamification_achievements",
        joinColumns = @JoinColumn(name = "user_gamification_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "achievement_type_id", referencedColumnName = "id"))
    private Set<Achievement> userAchievements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public UserGamification(User user, Double activityScoreBasedOnPoints) {
        this.user = user;
        this.activityScoreBasedOnPoints = activityScoreBasedOnPoints;
    }

    public UserGamification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getActivityScoreBasedOnPoints() {
        return activityScoreBasedOnPoints;
    }

    public void setActivityScoreBasedOnPoints(Double activityScoreBasedOnPoints) {
        this.activityScoreBasedOnPoints = activityScoreBasedOnPoints;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Achievement> getUserAchievements() {
        return userAchievements;
    }

    public void setUserAchievements(Set<Achievement> userAchievements) {
        this.userAchievements = userAchievements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGamification)) {
            return false;
        }
        return id != null && id.equals(((UserGamification) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, activityScoreBasedOnPoints, userAchievements);
    }

    @Override
    public String toString() {
        return "UserGamification{" +
            "id=" + getId() +
            "userId=" + getUser().getId() +
            ", activityScoreBasedOnPoints=" + getActivityScoreBasedOnPoints() +
            ", achievements=" + getUserAchievements() +
            "}";
    }
}
