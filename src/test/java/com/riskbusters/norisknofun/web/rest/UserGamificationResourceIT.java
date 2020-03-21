package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.repository.gamification.UserGamificationRepository;
import com.riskbusters.norisknofun.service.UserService;
import com.riskbusters.norisknofun.service.dto.UserGamificationDTO;
import com.riskbusters.norisknofun.service.gamification.UserGamificationService;
import com.riskbusters.norisknofun.service.mapper.UserGamificationMapper;
import com.riskbusters.norisknofun.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.riskbusters.norisknofun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserGamificationResource} REST controller.
 */
@SpringBootTest(classes = NoRiskNoFunApp.class)
// TODO: fix it
public class UserGamificationResourceIT {

    private static final Double DEFAULT_POINTS_SCORE = 1.0;
    private static final Double UPDATED_POINTS_SCORE = 2.0;

    @Autowired
    private UserGamificationRepository userGamificationRepository;

    @Mock
    private UserGamificationRepository userGamificationRepositoryMock;

    @Autowired
    private UserGamificationMapper userGamificationMapper;

    @Mock
    private UserGamificationService userGamificationServiceMock;

    @Autowired
    private UserGamificationService userGamificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUserGamificationMockMvc;

    private UserGamification userGamification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserGamificationResource userGamificationResource = new UserGamificationResource(userGamificationService, userService);
        this.restUserGamificationMockMvc = MockMvcBuilders.standaloneSetup(userGamificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGamification createEntity(EntityManager em) {
        User user = UserResourceIT.createEntity(em);
        em.persist(user);

        UserGamification userGamification = new UserGamification(user, DEFAULT_POINTS_SCORE);
        return userGamification;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGamification createUpdatedEntity(EntityManager em) {
        User user = UserResourceIT.createEntity(em);
        em.persist(user);

        UserGamification userGamification = new UserGamification(user, UPDATED_POINTS_SCORE);
        return userGamification;
    }

    @BeforeEach
    public void initTest() {
        userGamification = createEntity(em);
    }

    /* TODO @Test
    @Transactional
    public void createUserGamification() throws Exception {
        int databaseSizeBeforeCreate = userGamificationRepository.findAll().size();

        // Create the UserGamification
        UserGamificationDTO userGamificationDTO = userGamificationMapper.toUserGamificationDTO(userGamification);
        restUserGamificationMockMvc.perform(post("/api/user-gamifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGamificationDTO)))
            .andExpect(status().isOk());

        // Validate the UserGamification in the database
        List<UserGamification> userGamificationList = userGamificationRepository.findAll();
        assertThat(userGamificationList).hasSize(databaseSizeBeforeCreate + 1);
        UserGamification testUserGamification = userGamificationList.get(userGamificationList.size() - 1);
        assertThat(testUserGamification.getActivityScoreBasedOnPoints()).isEqualTo(DEFAULT_POINTS_SCORE);
    }

    @Test
    @Transactional
    public void createUserGamificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGamificationRepository.findAll().size();

        // Create the UserGamification with an existing ID
        userGamification.setId(1L);
        UserGamificationDTO userGamificationDTO = userGamificationMapper.toUserGamificationDTO(userGamification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGamificationMockMvc.perform(post("/api/user-gamifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGamificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGamification in the database
        List<UserGamification> userGamificationList = userGamificationRepository.findAll();
        assertThat(userGamificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserGamifications() throws Exception {
        // Initialize the database
        userGamificationRepository.saveAndFlush(userGamification);

        // Get all the userGamificationList
        restUserGamificationMockMvc.perform(get("/api/user-gamifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGamification.getId().intValue())))
            .andExpect(jsonPath("$.[*].pointsScore").value(hasItem(DEFAULT_POINTS_SCORE.intValue())));
    } */

/*    public void getAllUserGamificationsWithEagerRelationshipsIsEnabled() throws Exception {
        UserGamificationResource userGamificationResource = new UserGamificationResource(userGamificationServiceMock);
        Mockito.when(userGamificationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        MockMvc restUserGamificationMockMvc = MockMvcBuilders.standaloneSetup(userGamificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserGamificationMockMvc.perform(get("/api/user-gamifications?eagerload=true"))
        .andExpect(status().isOk());

        Mockito.verify(userGamificationServiceMock, Mockito.times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllUserGamificationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserGamificationResource userGamificationResource = new UserGamificationResource(userGamificationServiceMock);
            when(userGamificationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserGamificationMockMvc = MockMvcBuilders.standaloneSetup(userGamificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserGamificationMockMvc.perform(get("/api/user-gamifications?eagerload=true"))
        .andExpect(status().isOk());

            Mockito.verify(userGamificationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserGamification() throws Exception {
        // Initialize the database
        userGamificationRepository.saveAndFlush(userGamification);

        // Get the userGamification
        restUserGamificationMockMvc.perform(get("/api/user-gamifications/{id}", userGamification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userGamification.getId().intValue()))
            .andExpect(jsonPath("$.pointsScore").value(DEFAULT_POINTS_SCORE));
    } */

    /* TODO @Test
    @Transactional
    public void getNonExistingUserGamification() throws Exception {
        // Get the userGamification
        restUserGamificationMockMvc.perform(get("/api/user-gamifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    } */

    /* TODO @Test
    @Transactional
    public void updateUserGamification() throws Exception {
        // Initialize the database
        userGamificationRepository.saveAndFlush(userGamification);

        int databaseSizeBeforeUpdate = userGamificationRepository.findAll().size();

        // Update the userGamification
        UserGamification updatedUserGamification = userGamificationRepository.findById(userGamification.getId()).get();
        // Disconnect from session so that the updates on updatedUserGamification are not directly saved in db
        em.detach(updatedUserGamification);
        updatedUserGamification
            .pointsScore(UPDATED_POINTS_SCORE);
        UserGamificationDTO userGamificationDTO = userGamificationMapper.toUserGamificationDTO(updatedUserGamification);

        restUserGamificationMockMvc.perform(put("/api/user-gamifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGamificationDTO)))
            .andExpect(status().isOk());

        // Validate the UserGamification in the database
        List<UserGamification> userGamificationList = userGamificationRepository.findAll();
        assertThat(userGamificationList).hasSize(databaseSizeBeforeUpdate);
        UserGamification testUserGamification = userGamificationList.get(userGamificationList.size() - 1);
        assertThat(testUserGamification.getActivityScoreBasedOnPoints()).isEqualTo(UPDATED_POINTS_SCORE);
    } */

    @Test
    @Transactional
    public void updateNonExistingUserGamification() throws Exception {
        int databaseSizeBeforeUpdate = userGamificationRepository.findAll().size();

        // Create the UserGamification
        UserGamificationDTO userGamificationDTO = userGamificationMapper.toUserGamificationDTO(userGamification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGamificationMockMvc.perform(put("/api/user-gamifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGamificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGamification in the database
        List<UserGamification> userGamificationList = userGamificationRepository.findAll();
        assertThat(userGamificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserGamification() throws Exception {
        // Initialize the database
        userGamificationRepository.saveAndFlush(userGamification);

        int databaseSizeBeforeDelete = userGamificationRepository.findAll().size();

        // Delete the userGamification
        restUserGamificationMockMvc.perform(delete("/api/user-gamifications/{id}", userGamification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGamification> userGamificationList = userGamificationRepository.findAll();
        assertThat(userGamificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGamificationDTO.class);
        UserGamificationDTO userGamificationDTO1 = new UserGamificationDTO();
        userGamificationDTO1.setId(1L);
        UserGamificationDTO userGamificationDTO2 = new UserGamificationDTO();
        assertThat(userGamificationDTO1).isNotEqualTo(userGamificationDTO2);
        userGamificationDTO2.setId(userGamificationDTO1.getId());
        assertThat(userGamificationDTO1).isEqualTo(userGamificationDTO2);
        userGamificationDTO2.setId(2L);
        assertThat(userGamificationDTO1).isNotEqualTo(userGamificationDTO2);
        userGamificationDTO1.setId(null);
        assertThat(userGamificationDTO1).isNotEqualTo(userGamificationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        // assertThat(userGamificationMapper.(42L).getId()).isEqualTo(42);
        // assertThat(userGamificationMapper.fromId(null)).isNull();
    }
}
