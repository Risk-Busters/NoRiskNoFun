package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.service.UserGamificationService;
import com.riskbusters.norisknofun.service.UserService;
import com.riskbusters.norisknofun.web.rest.errors.BadRequestAlertException;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.riskbusters.norisknofun.domain.UserGamification}.
 */
@RestController
@RequestMapping("/api")
public class UserGamificationResource {

    private final Logger log = LoggerFactory.getLogger(UserGamificationResource.class);

    private static final String ENTITY_NAME = "userGamification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGamificationService userGamificationService;
    private final UserService userService;

    public UserGamificationResource(UserGamificationService userGamificationService, UserService userService) {
        this.userGamificationService = userGamificationService;
        this.userService = userService;
    }

    /**
     * {@code POST  /user-gamifications} : Create a new userGamification.
     *
     * @param userGamificationDTO the userGamificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGamificationDTO, or with status {@code 400 (Bad Request)} if the userGamification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-gamifications")
    public ResponseEntity<UserGamificationDTO> createUserGamification(@RequestBody UserGamificationDTO userGamificationDTO) throws URISyntaxException {
        log.debug("REST request to save UserGamification : {}", userGamificationDTO);
        if (userGamificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new userGamification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGamificationDTO result = userGamificationService.save(userGamificationDTO);
        return ResponseEntity.created(new URI("/api/user-gamifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-gamifications} : Updates an existing userGamification.
     *
     * @param userGamificationDTO the userGamificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGamificationDTO,
     * or with status {@code 400 (Bad Request)} if the userGamificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGamificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-gamifications")
    public ResponseEntity<UserGamificationDTO> updateUserGamification(@RequestBody UserGamificationDTO userGamificationDTO) throws URISyntaxException {
        log.debug("REST request to update UserGamification : {}", userGamificationDTO);
        if (userGamificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGamificationDTO result = userGamificationService.save(userGamificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGamificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-gamifications} : get all the userGamifications.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGamifications in body.
     */
    @GetMapping("/user-gamifications")
    public UserGamification getAllUserGamifications(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        Long userId = userService.getUserWithAuthorities().get().getId();
        log.debug("REST request to get all UserGamifications for user with id: {}", userId);
        return userGamificationService.findAllForOneUser(userId);
    }

    /**
     * {@code GET  /user-gamifications/:id} : get the "id" userGamification.
     *
     * @param id the id of the userGamificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGamificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-gamifications/{id}")
    public ResponseEntity<UserGamificationDTO> getUserGamification(@PathVariable Long id) {
        log.debug("REST request to get UserGamification : {}", id);
        Optional<UserGamificationDTO> userGamificationDTO = userGamificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGamificationDTO);
    }

    /**
     * {@code DELETE  /user-gamifications/:id} : delete the "id" userGamification.
     *
     * @param id the id of the userGamificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-gamifications/{id}")
    public ResponseEntity<Void> deleteUserGamification(@PathVariable Long id) {
        log.debug("REST request to delete UserGamification : {}", id);
        userGamificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}