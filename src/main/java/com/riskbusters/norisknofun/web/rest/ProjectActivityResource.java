package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.service.ProjectActivityService;
import com.riskbusters.norisknofun.service.UserService;
import com.riskbusters.norisknofun.service.dto.ProjectActivityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for managing {@link com.riskbusters.norisknofun.domain.ProjectActivityOverTime}.
 */
@RestController
@RequestMapping("/api")
public class ProjectActivityResource {

    private final Logger log = LoggerFactory.getLogger(ProjectActivityResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectActivityService projectActivityService;
    private final UserService userService;

    public ProjectActivityResource(UserService userService, ProjectActivityService projectActivityService) {
        this.userService = userService;
        this.projectActivityService = projectActivityService;
    }

    /**
     * {@code GET  /get-project-activity/:id} : get all the userGamifications for project with "id".
     *
     * @param projectId the id of the project.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the {@link ProjectActivityDTO}
     * with project Activity today
     * and the list (date, activity) for the past.
     */
    @GetMapping("/project-activity/{projectId}")
    public ProjectActivityDTO getProjectActivity(@PathVariable Long projectId) {
        Optional<User> user = userService.getUserWithAuthorities();
        if (user.isPresent()) {
            log.debug("REST request to get project activity for project with id: {}", projectId);
            ProjectActivityDTO result = projectActivityService.getProjectActivity(projectId);
            log.debug("RESULT: {}", result);
            return result;
        } else {
            return null;
        }
    }
}
