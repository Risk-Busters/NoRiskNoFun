package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.*;
import com.riskbusters.norisknofun.domain.enumeration.ProbabilityType;
import com.riskbusters.norisknofun.domain.enumeration.RiskDiscussionState;
import com.riskbusters.norisknofun.domain.enumeration.SeverityType;
import com.riskbusters.norisknofun.repository.ProjectRisksBaseRepository;
import com.riskbusters.norisknofun.repository.RiskDiscussionRepository;
import com.riskbusters.norisknofun.repository.RiskRepository;
import com.riskbusters.norisknofun.service.gamification.PointsOverTimeService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/*
 * Service for handling project risks.
 */
@Service
public class ProjectRiskService {


    private ProjectRisksBaseRepository projectRisksBaseRepository;
    private RiskRepository riskRepository;
    private RiskDiscussionRepository riskDiscussionRepository;
    private PointsOverTimeService pointsOverTimeService;
    private MessagingService messagingService;

    public ProjectRiskService(ProjectRisksBaseRepository projectRisksBaseRepository, RiskRepository riskRepository, RiskDiscussionRepository riskDiscussionRepository, PointsOverTimeService pointsOverTimeService, MessagingService messagingService) {
        this.projectRisksBaseRepository = projectRisksBaseRepository;
        this.riskRepository = riskRepository;
        this.riskDiscussionRepository = riskDiscussionRepository;
        this.pointsOverTimeService = pointsOverTimeService;
        this.messagingService = messagingService;
    }

    public ProjectRisks proposeProjectRisk(String title, String description, Project project, User userWhoProposed) {
        Risk risk = new Risk();
        risk.setName(title);
        risk.setDescription(description);
        risk.setInRiskpool(false);
        riskRepository.save(risk);

        ProjectRisks proposedProjectRisk = new ProjectRisks();
        proposedProjectRisk.riskDiscussionStatus = RiskDiscussionState.PROPOSED.getState();
        proposedProjectRisk.setRisk(risk);
        proposedProjectRisk.setProject(project);
        proposedProjectRisk.setHasOccured(false);

        rewardUser(PointsPerAction.PROPOSED_A_RISK, userWhoProposed);
        sendNotification(userWhoProposed);

        return  projectRisksBaseRepository.save(proposedProjectRisk);
    }

    /**
     * Saves an project risks including all changes.
     *
     * @param projectRisk the project risk to be saved / updated.
     * @return the project risk.
     */
    public ProjectRisks saveProjectRisk(ProjectRisks projectRisk) {
        riskRepository.save(projectRisk.getRisk());
        return projectRisksBaseRepository.save(projectRisk);
    }

    /**
     * Saves an project risk discussion.
     *
     * @return the project risk discussion.
     */
    public RiskDiscussion saveProjectRiskDiscussion(SeverityType severityType, ProbabilityType probabilityType, ProjectRisks projectRisk, User user) {
        RiskDiscussion discussion = new RiskDiscussion();
        discussion.setUser(user);
        discussion.setProjectProbability(probabilityType);
        discussion.setProjectSeverity(severityType);

        for (RiskDiscussion riskDiscussion : projectRisk.getDiscussions()) {
            if (riskDiscussion.getUser().equals(discussion.getUser())) {
                riskDiscussion.setProjectSeverity(severityType);
                riskDiscussion.setProjectProbability(probabilityType);

                riskDiscussionRepository.save(riskDiscussion);
                return riskDiscussion;
            }
        }

        riskDiscussionRepository.save(discussion);

        projectRisk.getDiscussions().add(discussion);
        projectRisksBaseRepository.save(projectRisk);

        return discussion;
    }

    /**
     * Update discussion status of a project risk.
     *
     * @param projectRisk the project risk to be updated.
     * @return if the project risk has been updated.
     */
    public boolean updateDiscussionStatus(ProjectRisks projectRisk) {
        switch (projectRisk.riskDiscussionStatus) {
            case "proposed":
                if (isDisscussable(projectRisk)) {
                    projectRisk.riskDiscussionStatus = "toBeDiscussed";
                    projectRisksBaseRepository.save(projectRisk);
                }
                break;
            case "toBeDiscussed":
                if (isFinal(projectRisk)) {
                    projectRisk.riskDiscussionStatus = "final";
                    projectRisksBaseRepository.save(projectRisk);
                }
                break;
            case "final":
                // TODO: Could implement logic to downgrade to discussion again here.
                break;
            default:
                return false;
        }

        return false;
    }


    private boolean isDisscussable(ProjectRisks projectRisks) {
        return projectRisks.getLikes() > 3;
    }

    private boolean isFinal(ProjectRisks projectRisks) {
        return projectRisks.getRiskResponses() != null && !projectRisks.getRiskResponses().isEmpty()
            && projectRisks.getPersonInCharge() != null
            && projectRisks.getDiscussions().size() >= 2;
    }

    /**
     * Add like to proposed project risk
     *
     * @param proposedProjectRisk the proposed project risk where a like should be added.
     * @param userWhoHasLiked the user who has liked the risk.
     * @return the updated proposed project risk.
     */
    public ProjectRisks addLikeToProposedProjectRisk(ProjectRisks proposedProjectRisk, User userWhoHasLiked) {
        proposedProjectRisk.addLike();
        rewardUser(PointsPerAction.REVIEWED_A_RISK, userWhoHasLiked);
        sendNotification(userWhoHasLiked);
        updateDiscussionStatus(proposedProjectRisk);
        return saveProjectRisk(proposedProjectRisk);
    }

    /**
     * Add person in charge to a discussed project risk
     *
     * @param discussedProjectRisk the discussed project risk where a person in Charge is added.
     * @param userInCharge user who is in charge of this risk.
     * @return the updated discussed project risk.
     */
    public ProjectRisks addPersonInCharge(ProjectRisks discussedProjectRisk, User userInCharge) {
        discussedProjectRisk.setPersonInCharge(userInCharge);
        rewardUser(PointsPerAction.BE_PERSON_IN_CHARGE, userInCharge);
        sendNotification(userInCharge);
        updateDiscussionStatus(discussedProjectRisk);
        return saveProjectRisk(discussedProjectRisk);
    }

    private void rewardUser(Points pointsToBeAdded,User userWhoProposed) {
        pointsOverTimeService.addPointsForToday(pointsToBeAdded, userWhoProposed);
    }

    private void sendNotification(User user) {
        Activity activity = new Activity();
        activity.setActivityDescriptionKey("noRiskNoFunApp.activity.reward");
        activity.setTargetUrl("/profile");
        Set<User> users = new HashSet<>();
        users.add(user);
        activity.setUsers(users);
        messagingService.addActivityWithNotification(activity);
    }
}
