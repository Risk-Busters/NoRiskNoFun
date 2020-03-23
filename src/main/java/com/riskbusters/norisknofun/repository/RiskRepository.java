package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.Risk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Risk entity.
 */
@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {

    @Query(value = "select distinct risk from Risk risk left join fetch risk.riskResponses",
        countQuery = "select count(distinct risk) from Risk risk")
    Page<Risk> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct risk from Risk risk left join fetch risk.riskResponses")
    List<Risk> findAllWithEagerRelationships();

    @Query("select risk from Risk risk left join fetch risk.riskResponses where risk.id =:id")
    Optional<Risk> findOneWithEagerRelationships(@Param("id") Long id);

}
