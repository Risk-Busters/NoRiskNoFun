package com.riskbusters.norisknofun.repository;
import com.riskbusters.norisknofun.domain.Risk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Risk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {

}
