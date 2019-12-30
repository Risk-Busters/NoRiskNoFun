package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.projectrisks.FinalProjectRisk;
import com.riskbusters.norisknofun.domain.projectrisks.ProposedProjectRisk;
import com.riskbusters.norisknofun.repository.FinalProjectRiskRepository;
import com.riskbusters.norisknofun.repository.ProposedProjectRiskRepository;
import com.riskbusters.norisknofun.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.riskbusters.norisknofun.domain.ProjectRisks}.
 */
@RestController
@RequestMapping("/api")
public class ProjectRisksResource {

    private final Logger log = LoggerFactory.getLogger(ProjectRisksResource.class);

    private static final String ENTITY_NAME = "projectRisks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProposedProjectRiskRepository proposedProjectRiskRepository;
    private final FinalProjectRiskRepository finalProjectRiskRepository;

    public ProjectRisksResource(ProposedProjectRiskRepository proposedProjectRiskRepository, FinalProjectRiskRepository finalProjectRiskRepository) {
        this.proposedProjectRiskRepository = proposedProjectRiskRepository;
        this.finalProjectRiskRepository = finalProjectRiskRepository;
    }

    /**
     * {@code POST  /project-risks} : Create a new proposedProjectRisk.
     *
     * @param proposedProjectRisk the proposedProjectRisk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proposedProjectRisk, or with status {@code 400 (Bad Request)} if the proposedProjectRisk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-risks")
    public ResponseEntity<ProjectRisks> createProjectRisks(@Valid @RequestBody ProposedProjectRisk proposedProjectRisk) throws URISyntaxException {
        log.debug("REST request to save ProjectRisks : {}", proposedProjectRisk);
        if (proposedProjectRisk.getId() != null) {
            throw new BadRequestAlertException("A new proposedProjectRisk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProposedProjectRisk result = proposedProjectRiskRepository.save(proposedProjectRisk);
        return ResponseEntity.created(new URI("/api/project-risks/" + result.getId()))
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
    @PutMapping("/project-risks")
    public ResponseEntity<ProjectRisks> updateProjectRisks(@Valid @RequestBody ProjectRisks projectRisks) {
        log.debug("REST request to update ProjectRisks : {}", projectRisks);
        if (projectRisks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectRisks result = proposedProjectRiskRepository.save((ProposedProjectRisk) projectRisks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectRisks.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-risks} : get all the projectRisks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRisks in body.
     */
    @GetMapping("/project-risks")
    public List<FinalProjectRisk> getAllProjectRisks(@RequestHeader("referer") URL url, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        long projectId;
        try {
            //TODO: maybe not the best way (hacky to get projectId out of referer URL)
            projectId = Long.parseLong(url.getPath().split("/")[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.debug("Error while getting projectId. Set projectId to -1");
            projectId = -1L;
        }

        log.debug("REST request to get all finalProjectRisks for project with id " + projectId);
        log.debug("all finalProjectRisks" + finalProjectRiskRepository.findAllByProject_Id(projectId).toString());
        return finalProjectRiskRepository.findAllByProject_Id(projectId);
    }

    /**
     * {@code GET  /project-risks/:id} : get the "id" projectRisks.
     *
     * @param id the id of the projectRisks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectRisks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-risks/{id}")
    public ResponseEntity<ProposedProjectRisk> getProjectRisks(@PathVariable Long id) {
        log.debug("REST request to get ProjectRisks : {}", id);
        Optional<ProposedProjectRisk> projectRisks = proposedProjectRiskRepository.findOneWithEagerRelationships(id);
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
        proposedProjectRiskRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
