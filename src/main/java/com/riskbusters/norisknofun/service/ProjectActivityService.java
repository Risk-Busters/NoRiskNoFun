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

import javax.persistence.EntityNotFoundException;
import java.util.*;

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

        List<PointWithDate> allProjectActivitiesOverTime = new ArrayList<>();
        for (ProjectActivityOverTime item : projectActivityOverTime) {
            allProjectActivitiesOverTime.add(new PointWithDate(item.getProjectActivityScoreAtThisDay(), item.getDate()));
        }

        PointWithDateComparator comparator = new PointWithDateComparator();
        allProjectActivitiesOverTime.sort(comparator);

        return new ProjectActivityDTO(projectActivityBasedOnUserScore, allProjectActivitiesOverTime);
    }

    private Double calculateProjectActivityScore(Long projectId) {
        Optional<Project> project = projectRepository.findOneWithEagerRelationships(projectId);
        if (project.isPresent()) {
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

            // save value over time
            createIfNotExists(project.get(), new CustomDate());
            ProjectActivityOverTime projectActivityScoreDBObject = projectActivityOverTimeRepository.findAllByProjectIdAndDate(project.get().getId(), new CustomDate());
            projectActivityScoreDBObject.setProjectActivityScoreAtThisDay(projectActivityScore);
            projectActivityOverTimeRepository.save(projectActivityScoreDBObject);
            return projectActivityScore;
        } else {
            throw new EntityNotFoundException();
        }

    }

    private void createIfNotExists(Project project, CustomDate date) {
        if (isNull(project, date)) {
            createEntry(project, date);
        }
    }

    private boolean isNull(Project project, CustomDate date) {
        ProjectActivityOverTime projectActivityOverTime = projectActivityOverTimeRepository.findAllByProjectIdAndDate(project.getId(), date);
        return projectActivityOverTime == null;
    }

    private void createEntry(Project project, CustomDate date) {
        ProjectActivityOverTime pointsOneDay = new ProjectActivityOverTime(project, 0.0, date);
        projectActivityOverTimeRepository.save(pointsOneDay);
    }
}
