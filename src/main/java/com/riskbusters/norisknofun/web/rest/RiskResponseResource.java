package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.RiskResponse;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.achievements.Achievement;
import com.riskbusters.norisknofun.repository.ProjectRisksBaseRepository;
import com.riskbusters.norisknofun.repository.RiskResponseRepository;
import com.riskbusters.norisknofun.service.AchievementService;
import com.riskbusters.norisknofun.service.ProjectRiskService;
import com.riskbusters.norisknofun.service.UserService;
import com.riskbusters.norisknofun.web.rest.errors.BadRequestAlertException;
import com.riskbusters.norisknofun.web.rest.vm.NewRiskResponseVM;
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
import java.util.Optional;

/**
 * REST controller for managing {@link com.riskbusters.norisknofun.domain.RiskResponse}.
 */
@RestController
@RequestMapping("/api")
public class RiskResponseResource {

    private final Logger log = LoggerFactory.getLogger(RiskResponseResource.class);

    private static final String ENTITY_NAME = "riskResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskResponseRepository riskResponseRepository;
    private final ProjectRisksBaseRepository projectRisksBaseRepository;
    private final ProjectRiskService projectRiskService;
    private final UserService userService;
    private final AchievementService achievementService;

    public RiskResponseResource(RiskResponseRepository riskResponseRepository, ProjectRisksBaseRepository projectRisksBaseRepository, ProjectRiskService projectRiskService, UserService userService, AchievementService achievementService) {
        this.riskResponseRepository = riskResponseRepository;
        this.projectRisksBaseRepository = projectRisksBaseRepository;
        this.projectRiskService = projectRiskService;
        this.userService = userService;
        this.achievementService = achievementService;
    }

    /**
     * {@code POST  /risk-responses} : Create a new riskResponse.
     *
     * @param riskResponseVM the riskResponse to create and potential projectrisk id.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskResponse, or with status {@code 400 (Bad Request)} if the riskResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Transactional
    @PostMapping("/risk-responses")
    public ResponseEntity<RiskResponse> createRiskResponse(@Valid @RequestBody NewRiskResponseVM riskResponseVM) throws URISyntaxException {
        log.debug("REST request to save RiskResponse : {}", riskResponseVM);
        if (riskResponseVM.getRiskResponse().getId() != null) {
            throw new BadRequestAlertException("A new riskResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }

        RiskResponse result = riskResponseRepository.save(riskResponseVM.getRiskResponse());

        if (riskResponseVM.getProjectRiskId() != null) {
            Optional<ProjectRisks> projectRisk = projectRisksBaseRepository.findById(riskResponseVM.getProjectRiskId());

            projectRisk.ifPresent(projectRisks -> {
                projectRisks.addRiskResponse(result);
                projectRisksBaseRepository.save(projectRisks);
                projectRiskService.updateDiscussionStatus(projectRisks);
            });
        }
        if (userService.getUserWithAuthorities().isPresent()) {
            User user = userService.getUserWithAuthorities().get();
            achievementService.handleBusterAchievement(user);
        }

        return ResponseEntity.created(new URI("/api/risk-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /risk-responses} : Updates an existing riskResponse.
     *
     * @param riskResponse the riskResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskResponse,
     * or with status {@code 400 (Bad Request)} if the riskResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/risk-responses")
    public ResponseEntity<RiskResponse> updateRiskResponse(@Valid @RequestBody RiskResponse riskResponse) throws URISyntaxException {
        log.debug("REST request to update RiskResponse : {}", riskResponse);
        if (riskResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiskResponse result = riskResponseRepository.save(riskResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskResponse.getId().toString()))
            .body(result);
    }

    /**
     * GET RISK RESPONSES VIA THE PROJECT RISK!
     * {@code GET  /risk-responses} : get all the riskResponses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskResponses in body.
    @GetMapping("/risk-responses")
    public List<RiskResponse> getAllRiskResponses() {
        log.debug("REST request to get all RiskResponses");
        return riskResponseRepository.findAll();
    }
    */

    /**
     * {@code GET  /risk-responses/:id} : get the "id" riskResponse.
     *
     * @param id the id of the riskResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/risk-responses/{id}")
    public ResponseEntity<RiskResponse> getRiskResponse(@PathVariable Long id) {
        log.debug("REST request to get RiskResponse : {}", id);
        Optional<RiskResponse> riskResponse = riskResponseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(riskResponse);
    }

    /**
     * {@code DELETE  /risk-responses/:id} : delete the "id" riskResponse.
     *
     * @param id the id of the riskResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/risk-responses/{id}")
    public ResponseEntity<Void> deleteRiskResponse(@PathVariable Long id) {
        log.debug("REST request to delete RiskResponse : {}", id);
        riskResponseRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
