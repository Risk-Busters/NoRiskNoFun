package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.repository.ProjectRisksRepository;
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

    private final ProjectRisksRepository projectRisksRepository;

    public ProjectRisksResource(ProjectRisksRepository projectRisksRepository) {
        this.projectRisksRepository = projectRisksRepository;
    }

    /**
     * {@code POST  /project-risks} : Create a new projectRisks.
     *
     * @param projectRisks the projectRisks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectRisks, or with status {@code 400 (Bad Request)} if the projectRisks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-risks")
    public ResponseEntity<ProjectRisks> createProjectRisks(@Valid @RequestBody ProjectRisks projectRisks) throws URISyntaxException {
        log.debug("REST request to save ProjectRisks : {}", projectRisks);
        if (projectRisks.getId() != null) {
            throw new BadRequestAlertException("A new projectRisks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectRisks result = projectRisksRepository.save(projectRisks);
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
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-risks")
    public ResponseEntity<ProjectRisks> updateProjectRisks(@Valid @RequestBody ProjectRisks projectRisks) throws URISyntaxException {
        log.debug("REST request to update ProjectRisks : {}", projectRisks);
        if (projectRisks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectRisks result = projectRisksRepository.save(projectRisks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectRisks.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-risks} : get all the projectRisks.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRisks in body.
     */
    @GetMapping("/project-risks")
    public List<ProjectRisks> getAllProjectRisks() {
        log.debug("REST request to get all ProjectRisks");
        return projectRisksRepository.findAll();
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
        Optional<ProjectRisks> projectRisks = projectRisksRepository.findById(id);
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
        projectRisksRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
