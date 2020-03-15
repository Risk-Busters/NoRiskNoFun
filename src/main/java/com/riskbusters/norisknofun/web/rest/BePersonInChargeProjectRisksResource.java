package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.Project;
import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.ProjectRepository;
import com.riskbusters.norisknofun.service.ProjectRiskService;
import com.riskbusters.norisknofun.service.UserService;
import com.riskbusters.norisknofun.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the like process for {@link ProjectRisks}.
 */
@RestController
@RequestMapping("/api")
public class BePersonInChargeProjectRisksResource {

    private final Logger log = LoggerFactory.getLogger(BePersonInChargeProjectRisksResource.class);

    private static final String ENTITY_NAME = "projectRisks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectRiskService projectRiskService;

    public BePersonInChargeProjectRisksResource(UserService userService, ProjectRepository projectRepository, ProjectRiskService projectRiskService) {
        this.userService = userService;
        this.projectRepository = projectRepository;
        this.projectRiskService = projectRiskService;
    }

    /**
     * {@code POST  /like-project-risks} : Add like for a proposed project risk.
     *
     * @param discussedProjectRisk the discussedProjectRisk to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectRisks,
     * or with status {@code 400 (Bad Request)} if the projectRisks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectRisks couldn't be updated.
     */
    @PostMapping("/person-in-charge-project-risks")
    public ResponseEntity<ProjectRisks> setPersonInCharge(@Valid @RequestBody ProjectRisks discussedProjectRisk) {
        log.debug("REST request to like proposed ProjectRisk: {}", discussedProjectRisk);

        Optional<User> user = userService.getUserWithAuthorities();
        if (!user.isPresent()) throw new BadRequestAlertException("Missing credentials", ENTITY_NAME, "usernull");

        Optional<Project> project = projectRepository.findByUsersIsContainingAndIdEquals(user.get(), discussedProjectRisk.getProject().getId());
        if (!project.isPresent()) {
            throw new BadRequestAlertException("User not in project", ENTITY_NAME, "projectnotexist");
        }

        ProjectRisks result = projectRiskService.addPersonInCharge(discussedProjectRisk, user.get());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discussedProjectRisk.getId().toString()))
            .body(result);
    }
}
