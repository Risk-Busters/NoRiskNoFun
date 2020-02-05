package com.riskbusters.norisknofun.domain.achievements;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.riskbusters.norisknofun.domain.enumeration.AchievementType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_achievements")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type")
@JsonSubTypes({
    @Type(value = ProjectMember.class, name = "ProjectMember"),
    @Type(value = RiskSage.class, name = "RiskSage"),
    @Type(value = RiskOwner.class, name = "RiskOwner"),
    @Type(value = RiskMaster.class, name = "RiskMaster"),
    @Type(value = RiskBuster.class, name = "RiskBuster"),
    @Type(value = ProjectManager.class, name = "ProjectManager")
})
public abstract class Achievement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    public AchievementType name;

    public AchievementType getName() {
        return name;
    }
}
