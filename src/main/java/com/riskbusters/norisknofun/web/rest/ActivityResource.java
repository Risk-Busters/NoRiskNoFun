package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.Activity;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.ActivityRepository;
import com.riskbusters.norisknofun.service.ActivityService;
import com.riskbusters.norisknofun.service.UserService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.riskbusters.norisknofun.domain.Activity}.
 */
@RestController
@RequestMapping("/api")
public class ActivityResource {

    private final Logger log = LoggerFactory.getLogger(ActivityResource.class);

    private static final String ENTITY_NAME = "activity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityRepository activityRepository;
    private final UserService userService;
    private final ActivityService activityService;

    public ActivityResource(ActivityRepository activityRepository, UserService userService, ActivityService activityService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
        this.activityService = activityService;
    }

    /**
     * {@code GET  /activities} : get all the activities.
     *

     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activities in body.
     */
    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getAllActivities(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Activities");
        User user = userService.getUserWithAuthorities().get();
        Page<Activity> page = activityService.getTranslatedActivityPage(pageable, user);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activities/:id} : get the "id" activity.
     *
     * @param id the id of the activity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activities/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable Long id) {
        log.debug("REST request to get Activity : {}", id);
        User user = userService.getUserWithAuthorities().get();
        Optional<Activity> activity = activityRepository.findOneWithEagerRelationships(id);
        activity.ifPresent(a -> {
            if (!a.getUsers().contains(user)) throw new AccessDeniedException("403: Forbidden");
        });
        return ResponseUtil.wrapOrNotFound(activity);
    }


    /**
     * {@code DELETE  /activities/:id} : drop out from the "id" activity.
     *
     * @param id the id of the activity to drop out from.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activities/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        log.debug("REST request to drop out from Activity : {}", id);
        User user = userService.getUserWithAuthorities().get();
        Optional<Activity> activity = activityRepository.findOneWithEagerRelationships(id);
        activityRepository.save(activity.get().removeUser(user));
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
