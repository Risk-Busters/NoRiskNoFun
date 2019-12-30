package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.projectrisks.FinalProjectRisk;
import com.riskbusters.norisknofun.domain.projectrisks.ToBeDiscussedProjectRisk;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DoBeDiscussedProjectRiskRepository extends ProjectRisksBaseRepository<ToBeDiscussedProjectRisk> {

    @Query("from ToBeDiscussedProjectRisk")
    List<FinalProjectRisk> getToBeDiscussedProjectRisk();

    List<FinalProjectRisk> findAllByProject_Id(Long id);
}
