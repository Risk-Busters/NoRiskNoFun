package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.projectrisks.FinalProjectRisk;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FinalProjectRiskRepository extends ProjectRisksBaseRepository<FinalProjectRisk> {

    @Query("from FinalProjectRisk ")
    List<FinalProjectRisk> getFinalProjectRisk();

    List<FinalProjectRisk> findAllByProject_Id(Long id);
}
