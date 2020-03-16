package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.*;
import com.riskbusters.norisknofun.repository.ProjectRepository;
import com.riskbusters.norisknofun.repository.gamification.ProjectActivityOverTimeRepository;
import com.riskbusters.norisknofun.repository.gamification.UserGamificationRepository;
import com.riskbusters.norisknofun.service.dto.ProjectActivityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link com.riskbusters.norisknofun.domain.ProjectActivityOverTime}.
 */
@Service
@Transactional
public class ProjectActivityService {

    private final Logger log = LoggerFactory.getLogger(ProjectActivityService.class);
    private final ProjectActivityOverTimeRepository projectActivityOverTimeRepository;
    private final ProjectRepository projectRepository;
    private final UserGamificationRepository userGamificationRepository;

    public ProjectActivityService(ProjectActivityOverTimeRepository projectActivityOverTimeRepository, ProjectRepository projectRepository, UserGamificationRepository userGamificationRepository) {
        this.projectActivityOverTimeRepository = projectActivityOverTimeRepository;
        this.projectRepository = projectRepository;
        this.userGamificationRepository = userGamificationRepository;
    }

    /**
     * Get project activity
     *
     * @param projectId of project for getting activity for
     */
    public ProjectActivityDTO getProjectActivity(Long projectId) {
        log.debug("Get project activity for project with id: {}", projectId);
        Double projectActivityBasedOnUserScore = calculateProjectActivityScore(projectId);

        List<ProjectActivityOverTime> projectActivityOverTime = projectActivityOverTimeRepository.findAllByProjectId(projectId);

        List<PointsWithDate> allProjectActivitiesOverTime = new ArrayList<>();
        for (ProjectActivityOverTime item : projectActivityOverTime) {
            allProjectActivitiesOverTime.add(new PointsWithDate(item.getProjectActivityAtThisDay().getPointsAsLong(), item.getDate()));
        }

        return new ProjectActivityDTO(projectActivityBasedOnUserScore, allProjectActivitiesOverTime);
    }

    private Double calculateProjectActivityScore(Long projectId) {
        Optional<Project> project = projectRepository.findOneWithEagerRelationships(projectId);
        Set<User> users = project.get().getUsers();
        users.add(project.get().getOwner());

        List<Double> activityScoreBasedOnPointsFromAllUsersOfProject = new ArrayList<>();
        for (User user : users) {
            activityScoreBasedOnPointsFromAllUsersOfProject.add(userGamificationRepository.findByUserId(user.getId()).getActivityScoreBasedOnPoints());
        }
        log.debug("Activity list: {}", activityScoreBasedOnPointsFromAllUsersOfProject);

        int numberOfProjectMembers = activityScoreBasedOnPointsFromAllUsersOfProject.size();
        Double sumOfUserActivityScores = activityScoreBasedOnPointsFromAllUsersOfProject.stream().mapToDouble(Double::doubleValue).sum();

        Double projectActivityScore = sumOfUserActivityScores / numberOfProjectMembers;
        return projectActivityScore;
    }
}
