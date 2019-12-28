package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.projectrisks.ProposedProjectRisk;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProposedProjectRiskRepository extends ProjectRisksBaseRepository<ProposedProjectRisk> {

    List<ProposedProjectRisk> findAllByProject_Id(Long id);
}
