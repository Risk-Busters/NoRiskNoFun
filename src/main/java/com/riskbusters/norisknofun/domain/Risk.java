package com.riskbusters.norisknofun.domain;
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
 * A Risk.
 */
@Entity
@Table(name = "risk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Risk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private SeverityType severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "probability")
    private ProbabilityType probability;

    @NotNull
    @Column(name = "in_riskpool", nullable = false)
    private Boolean inRiskpool;

    @OneToMany(mappedBy = "risk")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RiskResponse> riskResponses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Risk name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Risk description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SeverityType getSeverity() {
        return severity;
    }

    public Risk severity(SeverityType severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public ProbabilityType getProbability() {
        return probability;
    }

    public Risk probability(ProbabilityType probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(ProbabilityType probability) {
        this.probability = probability;
    }

    public Boolean isInRiskpool() {
        return inRiskpool;
    }

    public Risk inRiskpool(Boolean inRiskpool) {
        this.inRiskpool = inRiskpool;
        return this;
    }

    public void setInRiskpool(Boolean inRiskpool) {
        this.inRiskpool = inRiskpool;
    }

    public Set<RiskResponse> getRiskResponses() {
        return riskResponses;
    }

    public Risk riskResponses(Set<RiskResponse> riskResponses) {
        this.riskResponses = riskResponses;
        return this;
    }

    public Risk addRiskResponse(RiskResponse riskResponse) {
        this.riskResponses.add(riskResponse);
        riskResponse.setRisk(this);
        return this;
    }

    public Risk removeRiskResponse(RiskResponse riskResponse) {
        this.riskResponses.remove(riskResponse);
        riskResponse.setRisk(null);
        return this;
    }

    public void setRiskResponses(Set<RiskResponse> riskResponses) {
        this.riskResponses = riskResponses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Risk)) {
            return false;
        }
        return id != null && id.equals(((Risk) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Risk{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", severity='" + getSeverity() + "'" +
            ", probability='" + getProbability() + "'" +
            ", inRiskpool='" + isInRiskpool() + "'" +
            "}";
    }
}
