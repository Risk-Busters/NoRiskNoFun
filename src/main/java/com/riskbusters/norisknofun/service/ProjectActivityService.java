package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.PointsWithDate;
import com.riskbusters.norisknofun.domain.ProjectActivityOverTime;
import com.riskbusters.norisknofun.repository.gamification.ProjectActivityOverTimeRepository;
import com.riskbusters.norisknofun.service.dto.ProjectActivityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing {@link com.riskbusters.norisknofun.domain.ProjectActivityOverTime}.
 */
@Service
@Transactional
public class ProjectActivityService {

    private final Logger log = LoggerFactory.getLogger(ProjectActivityService.class);
    private final ProjectActivityOverTimeRepository projectActivityOverTimeRepository;

    public ProjectActivityService(ProjectActivityOverTimeRepository projectActivityOverTimeRepository) {
        this.projectActivityOverTimeRepository = projectActivityOverTimeRepository;
    }

    /**
     * Get project activity
     *
     * @param projectId of project for getting activity for
     */
    public ProjectActivityDTO getProjectActivity(Long projectId) {
        log.debug("Get project activity for project with id: {}", projectId);
        ProjectActivityOverTime projectActivityToday = projectActivityOverTimeRepository.findAllByProjectIdAndDate(projectId, new CustomDate());

        List<ProjectActivityOverTime> projectActivityOverTime = projectActivityOverTimeRepository.findAllByProjectId(projectId);

        List<PointsWithDate> allProjectActivitiesOverTime = new ArrayList<>();
        for (ProjectActivityOverTime item : projectActivityOverTime) {
            allProjectActivitiesOverTime.add(new PointsWithDate(item.getProjectActivityAtThisDay().getPointsAsLong(), item.getDate()));
        }

        return new ProjectActivityDTO(projectActivityToday.getProjectActivityAtThisDay().getPointsAsLong(), allProjectActivitiesOverTime);
    }
}
