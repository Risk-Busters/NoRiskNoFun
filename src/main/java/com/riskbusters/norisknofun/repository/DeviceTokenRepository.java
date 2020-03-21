package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.DeviceToken;
import com.riskbusters.norisknofun.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the {@link DeviceToken} entity.
 */
public interface DeviceTokenRepository  extends JpaRepository<DeviceToken, String> {

    List<DeviceToken> findAllByTokenOwnerIn(Set<User> users);
}
