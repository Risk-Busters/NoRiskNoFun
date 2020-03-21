package com.riskbusters.norisknofun.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Pair of device token and user that holds the token.
 */
@Entity
@Table(name = "device_token")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeviceToken {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    private String deviceToken;

    @NotNull
    @OneToOne
    private User tokenOwner;

    @CreatedDate
    @Column(insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public String getDeviceToken() {
        return deviceToken;
    }

    public User getTokenOwner() {
        return tokenOwner;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void setTokenOwner(User tokenOwner) {
        this.tokenOwner = tokenOwner;
    }
}
