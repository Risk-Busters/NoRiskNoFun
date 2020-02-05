package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.Project;
import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.Risk;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.projectrisks.ProposedProjectRisk;
import com.riskbusters.norisknofun.repository.ProjectRepository;
import com.riskbusters.norisknofun.repository.ProposedProjectRiskRepository;
import com.riskbusters.norisknofun.repository.RiskRepository;
import com.riskbusters.norisknofun.service.UserService;
import com.riskbusters.norisknofun.web.rest.errors.BadRequestAlertException;
import com.riskbusters.norisknofun.web.rest.vm.ProposeRiskVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ProjectRisks}.
 */
@RestController
@RequestMapping("/api")
public class ProposedProjectRisksController {

    private final Logger log = LoggerFactory.getLogger(ProposedProjectRisksController.class);

    private static final String ENTITY_NAME = "proposedProjectRisks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProposedProjectRiskRepository proposedProjectRiskRepository;

    private final RiskRepository riskRepository;

    private final ProjectRepository projectRepository;

    private final UserService userService;

    public ProposedProjectRisksController(ProposedProjectRiskRepository proposedProjectRiskRepository, RiskRepository riskRepository, ProjectRepository projectRepository, UserService userService) {
        this.proposedProjectRiskRepository = proposedProjectRiskRepository;
        this.riskRepository = riskRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /proposed-project-risks} : Create a new proposedProjectRisk.
     *
     * @param proposeRiskVM the proposedProjectRisk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proposedProjectRisk, or with status {@code 400 (Bad Request)} if the proposedProjectRisk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proposed-project-risks")
    public ResponseEntity<ProjectRisks> createProposedProjectRisks(@Valid @RequestBody ProposeRiskVM proposeRiskVM) throws URISyntaxException {
        log.debug("REST request to save new proposed ProjectRisk : {}", proposeRiskVM);

        Optional<User> user = userService.getUserWithAuthorities();
        if (!user.isPresent()) throw new BadRequestAlertException("Missing credentials", ENTITY_NAME, "usernull");

        Optional<Project> project = projectRepository.findByUsersIsContainingOrOwnerEqualsAndIdEquals(user.get(), user.get(), proposeRiskVM.getProjectId());
        if (!project.isPresent()) throw new BadRequestAlertException("User not in project", ENTITY_NAME, "projectnotexist");

        Risk risk = new Risk();
        risk.setName(proposeRiskVM.title);
        risk.setDescription(proposeRiskVM.description);
        risk.setInRiskpool(false);
        riskRepository.save(risk);

        ProposedProjectRisk proposedProjectRisk = new ProposedProjectRisk();
        proposedProjectRisk.setRisk(risk);
        proposedProjectRisk.setProject(project.get());
        proposedProjectRisk.setHasOccured(false);
        ProposedProjectRisk result = proposedProjectRiskRepository.save(proposedProjectRisk);

        return ResponseEntity.created(new URI("/api/project-risks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proposed-project-risks} : Updates an existing projectRisks.
     *
     * @param proposedProjectRisk the projectRisks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectRisks,
     * or with status {@code 400 (Bad Request)} if the projectRisks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectRisks couldn't be updated.
     */
    @PutMapping("/proposed-project-risks")
    public ResponseEntity<ProjectRisks> updateProposedProjectRisks(@Valid @RequestBody ProposedProjectRisk proposedProjectRisk) {
        log.debug("REST request to update ProjectRisks : {}", proposedProjectRisk);
        if (proposedProjectRisk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectRisks result = proposedProjectRiskRepository.save(proposedProjectRisk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proposedProjectRisk.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proposed-project-risks} : get all the projectRisks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRisks in body.
     */
    @GetMapping("/proposed-project-risks")
    public List<ProposedProjectRisk> getAllProposedProjectRisks(@RequestHeader("referer") URL url, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        long projectId;
        try {
            //TODO: maybe not the best way (hacky to get projectId out of referer URL)
            projectId = Long.parseLong(url.getPath().split("/")[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.debug("Error while getting projectId. Set projectId to -1");
            projectId = -1L;
        }

        log.debug("REST request to get all proposedProjectRisks for project with id " + projectId);
        return proposedProjectRiskRepository.findAllByProject_Id(projectId);
    }

    /**
     * {@code GET  /proposed-project-risks/:id} : get the "id" projectRisks.
     *
     * @param id the id of the projectRisks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectRisks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proposed-project-risks/{id}")
    public ResponseEntity<ProposedProjectRisk> getProposedProjectRisks(@PathVariable Long id) {
        log.debug("REST request to get ProjectRisks : {}", id);
        Optional<ProposedProjectRisk> projectRisks = proposedProjectRiskRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(projectRisks);
    }

    /**
     * {@code DELETE  /proposed-project-risks/:id} : delete the "id" projectRisks.
     *
     * @param id the id of the projectRisks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proposed-project-risks/{id}")
    public ResponseEntity<Void> deleteProposedProjectRisks(@PathVariable Long id) {
        log.debug("REST request to delete ProjectRisks : {}", id);
        proposedProjectRiskRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
