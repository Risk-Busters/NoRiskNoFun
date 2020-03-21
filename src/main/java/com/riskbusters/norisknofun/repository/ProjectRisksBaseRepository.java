package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.ProjectRisks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ProjectRisks entity.
 */
@Repository
@Transactional
public interface ProjectRisksBaseRepository extends JpaRepository<ProjectRisks, Long> {

    @Query(value = "select distinct projectRisks from ProjectRisks projectRisks left join fetch projectRisks.riskResponses",
        countQuery = "select count(distinct projectRisks) from ProjectRisks projectRisks")
    Page<ProjectRisks> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct projectRisks from ProjectRisks projectRisks left join fetch projectRisks.riskResponses")
    List<ProjectRisks> findAllWithEagerRelationships();

    @Query("select projectRisks from ProjectRisks projectRisks left join fetch projectRisks.riskResponses where projectRisks.id =:id")
    Optional<ProjectRisks> findOneWithEagerRelationships(@Param("id") Long id);

    List<ProjectRisks> findAllByProjectIdAndRiskDiscussionStatusEquals(Long id, String status);
}
