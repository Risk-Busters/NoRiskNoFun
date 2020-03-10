package com.riskbusters.norisknofun.repository;
import com.riskbusters.norisknofun.domain.RiskResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the RiskResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskResponseRepository extends JpaRepository<RiskResponse, Long> {
}
