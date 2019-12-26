package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.projectrisks.ToBeDiscussedProjectRisk;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DoBeDiscussedProjectRiskRepository extends ProjectRisksBaseRepository<ToBeDiscussedProjectRisk> {
}
