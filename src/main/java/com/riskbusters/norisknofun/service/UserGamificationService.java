package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.repository.UserGamificationRepository;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;
import com.riskbusters.norisknofun.service.mapper.UserGamificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserGamification}.
 */
@Service
@Transactional
public class UserGamificationService {

    private final Logger log = LoggerFactory.getLogger(UserGamificationService.class);

    private final UserGamificationRepository userGamificationRepository;

    private final UserGamificationMapper userGamificationMapper;

    public UserGamificationService(UserGamificationRepository userGamificationRepository, UserGamificationMapper userGamificationMapper) {
        this.userGamificationRepository = userGamificationRepository;
        this.userGamificationMapper = userGamificationMapper;
    }

    /**
     * Save a userGamification.
     *
     * @param userGamificationDTO the entity to save.
     * @return the persisted entity.
     */
    public UserGamificationDTO save(UserGamificationDTO userGamificationDTO) {
        log.debug("Request to save UserGamification : {}", userGamificationDTO);
        UserGamification userGamification = userGamificationMapper.toEntity(userGamificationDTO);
        userGamification = userGamificationRepository.save(userGamification);
        return userGamificationMapper.toDto(userGamification);
    }

    /**
     * Get all the userGamifications.
     *
     * @param id the id of the user.
     * @return the list of userGamifications for one specific user.
     */
    @Transactional(readOnly = true)
    public List<UserGamification> findAllForOneUser(Long id) {
        log.debug("Request to get all UserGamifications for user {}", id);
        return userGamificationRepository.findByUserId(id);
    }

    /**
     * Get all the userGamifications with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public List<UserGamification> findAllWithEagerRelationships() {
        return userGamificationRepository.findAllWithEagerRelationships();
    }


    /**
     * Get one userGamification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserGamificationDTO> findOne(Long id) {
        log.debug("Request to get UserGamification : {}", id);
        return userGamificationRepository.findOneWithEagerRelationships(id)
            .map(userGamificationMapper::toDto);
    }

    /**
     * Delete the userGamification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserGamification : {}", id);
        userGamificationRepository.deleteById(id);
    }
}
