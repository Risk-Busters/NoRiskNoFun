package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.ProjectRisks;
import com.riskbusters.norisknofun.repository.ProjectRisksRepository;
import com.riskbusters.norisknofun.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.riskbusters.norisknofun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.riskbusters.norisknofun.domain.enumeration.SeverityType;
import com.riskbusters.norisknofun.domain.enumeration.ProbabilityType;
/**
 * Integration tests for the {@link ProjectRisksResource} REST controller.
 */
@SpringBootTest(classes = NoRiskNoFunApp.class)
public class ProjectRisksResourceIT {

    private static final SeverityType DEFAULT_PROJECT_SEVERITY = SeverityType.BAD;
    private static final SeverityType UPDATED_PROJECT_SEVERITY = SeverityType.LESSBAD;

    private static final ProbabilityType DEFAULT_PROJECT_PROBABILITY = ProbabilityType.SURE;
    private static final ProbabilityType UPDATED_PROJECT_PROBABILITY = ProbabilityType.PROBABLY;

    private static final Boolean DEFAULT_HAS_OCCURED = false;
    private static final Boolean UPDATED_HAS_OCCURED = true;

    @Autowired
    private ProjectRisksRepository projectRisksRepository;

    @Mock
    private ProjectRisksRepository projectRisksRepositoryMock;

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

    private MockMvc restProjectRisksMockMvc;

    private ProjectRisks projectRisks;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectRisksResource projectRisksResource = new ProjectRisksResource(projectRisksRepository);
        this.restProjectRisksMockMvc = MockMvcBuilders.standaloneSetup(projectRisksResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectRisks createEntity(EntityManager em) {
        ProjectRisks projectRisks = new ProjectRisks()
            .projectSeverity(DEFAULT_PROJECT_SEVERITY)
            .projectProbability(DEFAULT_PROJECT_PROBABILITY)
            .hasOccured(DEFAULT_HAS_OCCURED);
        return projectRisks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectRisks createUpdatedEntity(EntityManager em) {
        ProjectRisks projectRisks = new ProjectRisks()
            .projectSeverity(UPDATED_PROJECT_SEVERITY)
            .projectProbability(UPDATED_PROJECT_PROBABILITY)
            .hasOccured(UPDATED_HAS_OCCURED);
        return projectRisks;
    }

    @BeforeEach
    public void initTest() {
        projectRisks = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectRisks() throws Exception {
        int databaseSizeBeforeCreate = projectRisksRepository.findAll().size();

        // Create the ProjectRisks
        restProjectRisksMockMvc.perform(post("/api/project-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectRisks)))
            .andExpect(status().isCreated());

        // Validate the ProjectRisks in the database
        List<ProjectRisks> projectRisksList = projectRisksRepository.findAll();
        assertThat(projectRisksList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectRisks testProjectRisks = projectRisksList.get(projectRisksList.size() - 1);
        assertThat(testProjectRisks.getProjectSeverity()).isEqualTo(DEFAULT_PROJECT_SEVERITY);
        assertThat(testProjectRisks.getProjectProbability()).isEqualTo(DEFAULT_PROJECT_PROBABILITY);
        assertThat(testProjectRisks.isHasOccured()).isEqualTo(DEFAULT_HAS_OCCURED);
    }

    @Test
    @Transactional
    public void createProjectRisksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRisksRepository.findAll().size();

        // Create the ProjectRisks with an existing ID
        projectRisks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectRisksMockMvc.perform(post("/api/project-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectRisks)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectRisks in the database
        List<ProjectRisks> projectRisksList = projectRisksRepository.findAll();
        assertThat(projectRisksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHasOccuredIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRisksRepository.findAll().size();
        // set the field null
        projectRisks.setHasOccured(null);

        // Create the ProjectRisks, which fails.

        restProjectRisksMockMvc.perform(post("/api/project-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectRisks)))
            .andExpect(status().isBadRequest());

        List<ProjectRisks> projectRisksList = projectRisksRepository.findAll();
        assertThat(projectRisksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectRisks() throws Exception {
        // Initialize the database
        projectRisksRepository.saveAndFlush(projectRisks);

        // Get all the projectRisksList
        restProjectRisksMockMvc.perform(get("/api/project-risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectRisks.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectSeverity").value(hasItem(DEFAULT_PROJECT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].projectProbability").value(hasItem(DEFAULT_PROJECT_PROBABILITY.toString())))
            .andExpect(jsonPath("$.[*].hasOccured").value(hasItem(DEFAULT_HAS_OCCURED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProjectRisksWithEagerRelationshipsIsEnabled() throws Exception {
        ProjectRisksResource projectRisksResource = new ProjectRisksResource(projectRisksRepositoryMock);
        when(projectRisksRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProjectRisksMockMvc = MockMvcBuilders.standaloneSetup(projectRisksResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProjectRisksMockMvc.perform(get("/api/project-risks?eagerload=true"))
        .andExpect(status().isOk());

        verify(projectRisksRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProjectRisksWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProjectRisksResource projectRisksResource = new ProjectRisksResource(projectRisksRepositoryMock);
            when(projectRisksRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProjectRisksMockMvc = MockMvcBuilders.standaloneSetup(projectRisksResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProjectRisksMockMvc.perform(get("/api/project-risks?eagerload=true"))
        .andExpect(status().isOk());

            verify(projectRisksRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProjectRisks() throws Exception {
        // Initialize the database
        projectRisksRepository.saveAndFlush(projectRisks);

        // Get the projectRisks
        restProjectRisksMockMvc.perform(get("/api/project-risks/{id}", projectRisks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectRisks.getId().intValue()))
            .andExpect(jsonPath("$.projectSeverity").value(DEFAULT_PROJECT_SEVERITY.toString()))
            .andExpect(jsonPath("$.projectProbability").value(DEFAULT_PROJECT_PROBABILITY.toString()))
            .andExpect(jsonPath("$.hasOccured").value(DEFAULT_HAS_OCCURED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectRisks() throws Exception {
        // Get the projectRisks
        restProjectRisksMockMvc.perform(get("/api/project-risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectRisks() throws Exception {
        // Initialize the database
        projectRisksRepository.saveAndFlush(projectRisks);

        int databaseSizeBeforeUpdate = projectRisksRepository.findAll().size();

        // Update the projectRisks
        ProjectRisks updatedProjectRisks = projectRisksRepository.findById(projectRisks.getId()).get();
        // Disconnect from session so that the updates on updatedProjectRisks are not directly saved in db
        em.detach(updatedProjectRisks);
        updatedProjectRisks
            .projectSeverity(UPDATED_PROJECT_SEVERITY)
            .projectProbability(UPDATED_PROJECT_PROBABILITY)
            .hasOccured(UPDATED_HAS_OCCURED);

        restProjectRisksMockMvc.perform(put("/api/project-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectRisks)))
            .andExpect(status().isOk());

        // Validate the ProjectRisks in the database
        List<ProjectRisks> projectRisksList = projectRisksRepository.findAll();
        assertThat(projectRisksList).hasSize(databaseSizeBeforeUpdate);
        ProjectRisks testProjectRisks = projectRisksList.get(projectRisksList.size() - 1);
        assertThat(testProjectRisks.getProjectSeverity()).isEqualTo(UPDATED_PROJECT_SEVERITY);
        assertThat(testProjectRisks.getProjectProbability()).isEqualTo(UPDATED_PROJECT_PROBABILITY);
        assertThat(testProjectRisks.isHasOccured()).isEqualTo(UPDATED_HAS_OCCURED);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectRisks() throws Exception {
        int databaseSizeBeforeUpdate = projectRisksRepository.findAll().size();

        // Create the ProjectRisks

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectRisksMockMvc.perform(put("/api/project-risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectRisks)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectRisks in the database
        List<ProjectRisks> projectRisksList = projectRisksRepository.findAll();
        assertThat(projectRisksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectRisks() throws Exception {
        // Initialize the database
        projectRisksRepository.saveAndFlush(projectRisks);

        int databaseSizeBeforeDelete = projectRisksRepository.findAll().size();

        // Delete the projectRisks
        restProjectRisksMockMvc.perform(delete("/api/project-risks/{id}", projectRisks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectRisks> projectRisksList = projectRisksRepository.findAll();
        assertThat(projectRisksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectRisks.class);
        ProjectRisks projectRisks1 = new ProjectRisks();
        projectRisks1.setId(1L);
        ProjectRisks projectRisks2 = new ProjectRisks();
        projectRisks2.setId(projectRisks1.getId());
        assertThat(projectRisks1).isEqualTo(projectRisks2);
        projectRisks2.setId(2L);
        assertThat(projectRisks1).isNotEqualTo(projectRisks2);
        projectRisks1.setId(null);
        assertThat(projectRisks1).isNotEqualTo(projectRisks2);
    }
}
