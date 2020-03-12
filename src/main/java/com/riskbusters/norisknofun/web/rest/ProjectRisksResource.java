package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.Project;
import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.RiskDiscussion;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.enumeration.RiskDiscussionState;
import com.riskbusters.norisknofun.repository.ProjectRepository;
import com.riskbusters.norisknofun.repository.ProjectRisksBaseRepository;
import com.riskbusters.norisknofun.repository.RiskRepository;
import com.riskbusters.norisknofun.service.ProjectRiskService;
import com.riskbusters.norisknofun.service.UserService;
import com.riskbusters.norisknofun.web.rest.errors.BadRequestAlertException;
import com.riskbusters.norisknofun.web.rest.vm.DiscussRiskVM;
import com.riskbusters.norisknofun.web.rest.vm.ProposeRiskVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.riskbusters.norisknofun.domain.ProjectRisks}.
 */
@RestController
@RequestMapping("/api")
public class ProjectRisksResource {

    private final Logger log = LoggerFactory.getLogger(ProjectRisksResource.class);

    private static final String ENTITY_NAME = "projectRisks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectRisksBaseRepository projectRisksBaseRepository;
    private final ProjectRiskService projectRiskService;

    public ProjectRisksResource(ProjectRisksBaseRepository projectRisksBaseRepository, RiskRepository riskRepository, UserService userService, ProjectRepository projectRepository, ProjectRiskService projectRiskService) {
        this.projectRisksBaseRepository = projectRisksBaseRepository;
        this.userService = userService;
        this.projectRepository = projectRepository;
        this.projectRiskService = projectRiskService;
    }

    /**
     * {@code POST  /project-risks} : Create a new proposedProjectRisk.
     *
     * @param proposedProjectRisk the proposedProjectRisk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proposedProjectRisk, or with status {@code 400 (Bad Request)} if the proposedProjectRisk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-risks")
    public ResponseEntity<ProjectRisks> createProjectRisks(@Valid @RequestBody ProposeRiskVM proposedProjectRisk) throws URISyntaxException {
        log.debug("REST request to save new proposed ProjectRisk : {}", proposedProjectRisk);

        Optional<User> user = userService.getUserWithAuthorities();
        if (!user.isPresent()) throw new BadRequestAlertException("Missing credentials", ENTITY_NAME, "usernull");

        Optional<Project> project = projectRepository.findByUsersIsContainingAndIdEquals(user.get(), proposedProjectRisk.getProjectId());
        if (!project.isPresent()) throw new BadRequestAlertException("User not in project", ENTITY_NAME, "projectnotexist");

        ProjectRisks result = projectRiskService.proposeProjectRisk(proposedProjectRisk.title, proposedProjectRisk.description, project.get());
        return ResponseEntity.created(new URI("/api/project-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @Transactional
    @PostMapping("/project-risks-discussion")
    public ResponseEntity<RiskDiscussion> createProjectRisksDiscussion(@Valid @RequestBody DiscussRiskVM discussRiskVM) throws URISyntaxException {
        log.debug("REST request to save new risk discussion : {}", discussRiskVM);

        Optional<User> user = userService.getUserWithAuthorities();
        if (!user.isPresent()) throw new BadRequestAlertException("Missing credentials", ENTITY_NAME, "usernull");

        Optional<ProjectRisks> projectRisks = projectRisksBaseRepository.findById(discussRiskVM.getProjectRiskId());
        if (!projectRisks.isPresent()) throw new BadRequestAlertException("Project risk doesnt exist", ENTITY_NAME, "projectrisknotexist");

        RiskDiscussion result = projectRiskService.saveProjectRiskDiscussion(discussRiskVM.getProjectSeverity(), discussRiskVM.getProjectProbability(), projectRisks.get(), user.get());
        projectRiskService.updateDiscussionStatus(projectRisks.get());

        return ResponseEntity.created(new URI("/api/project-risks-discussion/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * {@code PUT  /project-risks} : Updates an existing projectRisks.
     *
     * @param projectRisks the projectRisks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectRisks,
     * or with status {@code 400 (Bad Request)} if the projectRisks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectRisks couldn't be updated.
     */
    @PutMapping(value = "/project-risks")
    public ResponseEntity<ProjectRisks> updateProjectRisks(@Valid @RequestBody ProjectRisks projectRisks) {
        log.debug("REST request to update ProjectRisks : {}", projectRisks);
        if (projectRisks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        ProjectRisks result = projectRiskService.saveProjectRisk(projectRisks);
        projectRiskService.updateDiscussionStatus(projectRisks);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectRisks.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-risks} : get all final projectRisks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRisks in body.
     */
    @GetMapping("/project-risks")
    public List<ProjectRisks> getAllProjectRisks(@RequestHeader("referer") URL url, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        long projectId;
        try {
            projectId = Long.parseLong(url.getPath().split("/")[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.debug("Error while getting projectId. Set projectId to -1");
            projectId = -1L;
        }

        log.debug("REST request to get all finalProjectRisks for project with id " + projectId);
        log.debug("all finalProjectRisks" + projectRisksBaseRepository.findAllByProjectIdAndRiskDiscussionStatusEquals(projectId, RiskDiscussionState.FINAL.getState()));
        return projectRisksBaseRepository.findAllByProjectIdAndRiskDiscussionStatusEquals(projectId, RiskDiscussionState.FINAL.getState());
    }

    /**
     * {@code GET  /project-risks} : get all discussion projectRisks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRisks in body.
     */
    @GetMapping("/discuss-project-risks")
    public List<ProjectRisks> getAllDiscussProjectRisks(@RequestHeader("referer") URL url, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        long projectId;
        try {
            projectId = Long.parseLong(url.getPath().split("/")[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.debug("Error while getting projectId. Set projectId to -1");
            projectId = -1L;
        }

        log.debug("REST request to get all proposedProjectRisks for project with id " + projectId);
        return projectRisksBaseRepository.findAllByProjectIdAndRiskDiscussionStatusEquals(projectId, RiskDiscussionState.DISCUSSION.getState());
    }

    /**
     * {@code GET  /project-risks} : get all proposed projectRisks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRisks in body.
     */
    @GetMapping("/proposed-project-risks")
    public List<ProjectRisks> getAllProposedProjectRisks(@RequestHeader("referer") URL url, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        long projectId;
        try {
            projectId = Long.parseLong(url.getPath().split("/")[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.debug("Error while getting projectId. Set projectId to -1");
            projectId = -1L;
        }

        log.debug("REST request to get all proposedProjectRisks for project with id " + projectId);
        return projectRisksBaseRepository.findAllByProjectIdAndRiskDiscussionStatusEquals(projectId, RiskDiscussionState.PROPOSED.getState());
    }

    /**
     * {@code GET  /project-risks/:id} : get the "id" projectRisks.
     *
     * @param id the id of the projectRisks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectRisks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-risks/{id}")
    public ResponseEntity<ProjectRisks> getProjectRisks(@PathVariable Long id) {
        log.debug("REST request to get ProjectRisks : {}", id);
        Optional<ProjectRisks> projectRisks = projectRisksBaseRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(projectRisks);
    }

    /**
     * {@code DELETE  /project-risks/:id} : delete the "id" projectRisks.
     *
     * @param id the id of the projectRisks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-risks/{id}")
    public ResponseEntity<Void> deleteProjectRisks(@PathVariable Long id) {
        log.debug("REST request to delete ProjectRisks : {}", id);
        projectRisksBaseRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
