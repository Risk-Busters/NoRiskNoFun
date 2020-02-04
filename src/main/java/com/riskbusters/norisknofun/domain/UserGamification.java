package com.riskbusters.norisknofun.domain;

import com.riskbusters.norisknofun.domain.achievements.Achievement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
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

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @Column(name = "points_score")
    @Embedded
    private Points pointsScore;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_gamification_achievements",
        joinColumns = @JoinColumn(name = "user_gamification_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "achievement_type_id", referencedColumnName = "id"))
    private Set<Achievement> userAchievements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Points getPointsScore() {
        return pointsScore;
    }

    public UserGamification pointsScore(Points pointsScore) {
        this.pointsScore = pointsScore;
        return this;
    }

    public void addPoints(Points pointsToAdd) {
        this.pointsScore.addPoints(pointsToAdd);
    }

    public void setPointsScore(Points pointsScore) {
        this.pointsScore = pointsScore;
    }

    public User getUser() {
        return user;
    }

    public UserGamification user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Achievement> getUserAchievements() {
        return userAchievements;
    }

    public UserGamification achievementTypes(Set<Achievement> userAchievementsTypes) {
        this.userAchievements = userAchievementsTypes;
        return this;
    }

    public UserGamification addAchievementType(Achievement userAchievement) {
        this.userAchievements.add(userAchievement);
        // TODO: achievementType.getUserGamifications().add(this);
        return this;
    }

    public UserGamification removeAchievementType(Achievement userAchievements) {
        this.userAchievements.remove(userAchievements);
        // TODO: achievementType.getUserGamifications().remove(this);
        return this;
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
        return 31;
    }

    @Override
    public String toString() {
        return "UserGamification{" +
            "id=" + getId() +
            ", pointsScore=" + getPointsScore() +
            "}";
    }
}
