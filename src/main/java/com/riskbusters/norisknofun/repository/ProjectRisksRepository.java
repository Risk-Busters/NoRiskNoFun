package com.riskbusters.norisknofun.repository;
import com.riskbusters.norisknofun.domain.ProjectRisks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectRisks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRisksRepository extends JpaRepository<ProjectRisks, Long> {

}
