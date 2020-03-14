package com.riskbusters.norisknofun.repository.gamification;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.ProjectActivityOverTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ProjectActivityOverTime entity.
 */
@Repository
public interface ProjectActivityOverTimeRepository extends JpaRepository<ProjectActivityOverTime, Long> {

    List<ProjectActivityOverTime> findAllByProjectId(Long projectId);

    ProjectActivityOverTime findAllByProjectIdAndDate(Long projectId, CustomDate date);
}
