package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.*;
import com.riskbusters.norisknofun.domain.enumeration.ProbabilityType;
import com.riskbusters.norisknofun.domain.enumeration.RiskDiscussionState;
import com.riskbusters.norisknofun.domain.enumeration.SeverityType;
import com.riskbusters.norisknofun.repository.*;
import org.springframework.stereotype.Service;

/*
 * Service for handling project risks.
 */
@Service
public class ProjectRiskService {


    private ProjectRisksBaseRepository projectRisksBaseRepository;
    private RiskRepository riskRepository;
    private RiskDiscussionRepository riskDiscussionRepository;

    public ProjectRiskService(ProjectRisksBaseRepository projectRisksBaseRepository, RiskRepository riskRepository, UserService userService, ProjectRepository projectRepository, RiskDiscussionRepository riskDiscussionRepository) {
        this.projectRisksBaseRepository = projectRisksBaseRepository;
        this.riskRepository = riskRepository;
        this.riskDiscussionRepository = riskDiscussionRepository;
    }

    public ProjectRisks proposeProjectRisk(String title, String description, Project project) {
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
        discussion.setProjectProbability(probabilityType);
        discussion.setProjectSeverity(severityType);

        projectRisk.putDiscussion(discussion, user);
        riskDiscussionRepository.save(discussion);
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
}
