package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.projectrisks.FinalProjectRisk;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FinalProjectRiskRepository extends ProjectRisksBaseRepository<FinalProjectRisk> {
}
