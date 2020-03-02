package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.Project;
import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.Risk;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.enumeration.RiskDiscussionState;
import com.riskbusters.norisknofun.repository.*;
import com.riskbusters.norisknofun.web.rest.errors.BadRequestAlertException;
import com.riskbusters.norisknofun.web.rest.vm.ProposeRiskVM;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * Service for handling project risks.
 */
@Service
public class ProjectRiskService {


    private ProjectRisksBaseRepository projectRisksBaseRepository;
    private RiskRepository riskRepository;

    public ProjectRiskService(ProjectRisksBaseRepository projectRisksBaseRepository, RiskRepository riskRepository, UserService userService, ProjectRepository projectRepository) {
        this.projectRisksBaseRepository = projectRisksBaseRepository;
        this.riskRepository = riskRepository;
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
        return true;
    }

    private boolean isFinal(ProjectRisks projectRisks) {
        return !projectRisks.getRisk().getRiskResponses().isEmpty()
            && projectRisks.getRisk().getProbability() != null
            && projectRisks.getRisk().getSeverity() != null;
    }
}
