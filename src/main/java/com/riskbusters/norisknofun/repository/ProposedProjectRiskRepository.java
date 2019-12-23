package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.projectrisks.ProposedProjectRisk;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProposedProjectRiskRepository extends ProjectRisksBaseRepository<ProposedProjectRisk> {
}
