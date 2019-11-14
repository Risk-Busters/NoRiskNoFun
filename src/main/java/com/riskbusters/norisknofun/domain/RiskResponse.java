package com.riskbusters.norisknofun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

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

    @ManyToOne
    @JsonIgnoreProperties("riskResponses")
    private Risk risk;

    @ManyToOne
    @JsonIgnoreProperties("riskResponses")
    private ProjectRisks projectRisks;

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

    public Risk getRisk() {
        return risk;
    }

    public RiskResponse risk(Risk risk) {
        this.risk = risk;
        return this;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public ProjectRisks getProjectRisks() {
        return projectRisks;
    }

    public RiskResponse projectRisks(ProjectRisks projectRisks) {
        this.projectRisks = projectRisks;
        return this;
    }

    public void setProjectRisks(ProjectRisks projectRisks) {
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
