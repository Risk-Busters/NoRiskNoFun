package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.projectrisks.ProposedProjectRisk;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProposedProjectRiskRepository extends ProjectRisksBaseRepository<ProposedProjectRisk> {

    @Query("from ProposedProjectRisk")
    List<ProposedProjectRisk> getProposedProjectRisks();

    List<ProposedProjectRisk> findAllByProject_Id(Long id);
}
