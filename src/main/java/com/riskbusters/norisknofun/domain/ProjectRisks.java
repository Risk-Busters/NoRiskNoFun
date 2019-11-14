package com.riskbusters.norisknofun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.riskbusters.norisknofun.domain.enumeration.SeverityType;

import com.riskbusters.norisknofun.domain.enumeration.ProbabilityType;

/**
 * A ProjectRisks.
 */
@Entity
@Table(name = "project_risks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectRisks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_severity")
    private SeverityType projectSeverity;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_probability")
    private ProbabilityType projectProbability;

    @NotNull
    @Column(name = "has_occured", nullable = false)
    private Boolean hasOccured;

    @OneToMany(mappedBy = "projectRisks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RiskResponse> riskResponses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("projectRisks")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeverityType getProjectSeverity() {
        return projectSeverity;
    }

    public ProjectRisks projectSeverity(SeverityType projectSeverity) {
        this.projectSeverity = projectSeverity;
        return this;
    }

    public void setProjectSeverity(SeverityType projectSeverity) {
        this.projectSeverity = projectSeverity;
    }

    public ProbabilityType getProjectProbability() {
        return projectProbability;
    }

    public ProjectRisks projectProbability(ProbabilityType projectProbability) {
        this.projectProbability = projectProbability;
        return this;
    }

    public void setProjectProbability(ProbabilityType projectProbability) {
        this.projectProbability = projectProbability;
    }

    public Boolean isHasOccured() {
        return hasOccured;
    }

    public ProjectRisks hasOccured(Boolean hasOccured) {
        this.hasOccured = hasOccured;
        return this;
    }

    public void setHasOccured(Boolean hasOccured) {
        this.hasOccured = hasOccured;
    }

    public Set<RiskResponse> getRiskResponses() {
        return riskResponses;
    }

    public ProjectRisks riskResponses(Set<RiskResponse> riskResponses) {
        this.riskResponses = riskResponses;
        return this;
    }

    public ProjectRisks addRiskResponse(RiskResponse riskResponse) {
        this.riskResponses.add(riskResponse);
        riskResponse.setProjectRisks(this);
        return this;
    }

    public ProjectRisks removeRiskResponse(RiskResponse riskResponse) {
        this.riskResponses.remove(riskResponse);
        riskResponse.setProjectRisks(null);
        return this;
    }

    public void setRiskResponses(Set<RiskResponse> riskResponses) {
        this.riskResponses = riskResponses;
    }

    public Project getProject() {
        return project;
    }

    public ProjectRisks project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectRisks)) {
            return false;
        }
        return id != null && id.equals(((ProjectRisks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProjectRisks{" +
            "id=" + getId() +
            ", projectSeverity='" + getProjectSeverity() + "'" +
            ", projectProbability='" + getProjectProbability() + "'" +
            ", hasOccured='" + isHasOccured() + "'" +
            "}";
    }
}
