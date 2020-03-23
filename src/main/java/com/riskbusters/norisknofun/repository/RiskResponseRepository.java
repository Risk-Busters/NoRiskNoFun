package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.RiskResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RiskResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskResponseRepository extends JpaRepository<RiskResponse, Long> {
}
