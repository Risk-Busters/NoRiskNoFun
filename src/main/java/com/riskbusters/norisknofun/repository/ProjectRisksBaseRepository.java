package com.riskbusters.norisknofun.repository;
import com.riskbusters.norisknofun.domain.ProjectRisks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ProjectRisks entity.
 */
@NoRepositoryBean
public interface ProjectRisksBaseRepository<T extends ProjectRisks> extends JpaRepository<T, Long> {

    @Query(value = "select distinct projectRisks from ProjectRisks projectRisks left join fetch projectRisks.riskResponses",
        countQuery = "select count(distinct projectRisks) from ProjectRisks projectRisks")
    Page<T> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct projectRisks from ProjectRisks projectRisks left join fetch projectRisks.riskResponses")
    List<T> findAllWithEagerRelationships();

    @Query("select projectRisks from ProjectRisks projectRisks left join fetch projectRisks.riskResponses where projectRisks.id =:id")
    Optional<T> findOneWithEagerRelationships(@Param("id") Long id);

}
