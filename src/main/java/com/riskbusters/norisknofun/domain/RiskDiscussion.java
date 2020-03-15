package com.riskbusters.norisknofun.domain;

import com.riskbusters.norisknofun.domain.enumeration.ProbabilityType;
import com.riskbusters.norisknofun.domain.enumeration.SeverityType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class RiskDiscussion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "project_severity")
    private SeverityType projectSeverity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "project_probability")
    private ProbabilityType projectProbability;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeverityType getProjectSeverity() {
        return projectSeverity;
    }

    public void setProjectSeverity(SeverityType projectSeverity) {
        this.projectSeverity = projectSeverity;
    }

    public ProbabilityType getProjectProbability() {
        return projectProbability;
    }

    public void setProjectProbability(ProbabilityType projectProbability) {
        this.projectProbability = projectProbability;
    }
}
