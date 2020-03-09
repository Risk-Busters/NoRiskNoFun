package com.riskbusters.norisknofun.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.riskbusters.norisknofun.domain.enumeration.RiskResponseType;

import com.riskbusters.norisknofun.domain.enumeration.StatusType;

/**
 * A RiskResponse.
 */
@Entity
@Table(name = "risk_response")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RiskResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RiskResponseType type;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @ManyToMany(mappedBy = "riskResponses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Risk> risks = new HashSet<>();

    @ManyToMany(mappedBy = "riskResponses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ProjectRisks> projectRisks = new HashSet<>();

    @PreRemove
    private void removeProjectRisks() {
        for (ProjectRisks pR : projectRisks) {
            pR.removeRiskResponse(this);
        }
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RiskResponseType getType() {
        return type;
    }

    public RiskResponse type(RiskResponseType type) {
        this.type = type;
        return this;
    }

    public void setType(RiskResponseType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public RiskResponse description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusType getStatus() {
        return status;
    }

    public RiskResponse status(StatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Set<Risk> getRisks() {
        return risks;
    }

    public RiskResponse risks(Set<Risk> risks) {
        this.risks = risks;
        return this;
    }

    public RiskResponse addRisk(Risk risk) {
        this.risks.add(risk);
        risk.getRiskResponses().add(this);
        return this;
    }

    public RiskResponse removeRisk(Risk risk) {
        this.risks.remove(risk);
        risk.getRiskResponses().remove(this);
        return this;
    }

    public void setRisks(Set<Risk> risks) {
        this.risks = risks;
    }

    public Set<ProjectRisks> getProjectRisks() {
        return projectRisks;
    }

    public RiskResponse projectRisks(Set<ProjectRisks> projectRisks) {
        this.projectRisks = projectRisks;
        return this;
    }

    public RiskResponse addProjectRisks(ProjectRisks projectRisks) {
        this.projectRisks.add(projectRisks);
        projectRisks.getRiskResponses().add(this);
        return this;
    }

    public RiskResponse removeProjectRisks(ProjectRisks projectRisks) {
        this.projectRisks.remove(projectRisks);
        projectRisks.getRiskResponses().remove(this);
        return this;
    }

    public void setProjectRisks(Set<ProjectRisks> projectRisks) {
        this.projectRisks = projectRisks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskResponse)) {
            return false;
        }
        return id != null && id.equals(((RiskResponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RiskResponse{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
