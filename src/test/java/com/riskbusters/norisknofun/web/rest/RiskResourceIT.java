package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.Risk;
import com.riskbusters.norisknofun.repository.RiskRepository;
import com.riskbusters.norisknofun.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import com.riskbusters.norisknofun.domain.enumeration.SeverityType;
import com.riskbusters.norisknofun.domain.enumeration.ProbabilityType;
/**
 * Integration tests for the {@link RiskResource} REST controller.
 */
@SpringBootTest(classes = NoRiskNoFunApp.class)
public class RiskResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final SeverityType DEFAULT_SEVERITY = SeverityType.BAD;
    private static final SeverityType UPDATED_SEVERITY = SeverityType.LESSBAD;

    private static final ProbabilityType DEFAULT_PROBABILITY = ProbabilityType.SURE;
    private static final ProbabilityType UPDATED_PROBABILITY = ProbabilityType.PROBABLY;

    private static final Boolean DEFAULT_IN_RISKPOOL = false;
    private static final Boolean UPDATED_IN_RISKPOOL = true;

    @Autowired
    private RiskRepository riskRepository;

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

    private MockMvc restRiskMockMvc;

    private Risk risk;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RiskResource riskResource = new RiskResource(riskRepository);
        this.restRiskMockMvc = MockMvcBuilders.standaloneSetup(riskResource)
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
    public static Risk createEntity(EntityManager em) {
        Risk risk = new Risk()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .severity(DEFAULT_SEVERITY)
            .probability(DEFAULT_PROBABILITY)
            .inRiskpool(DEFAULT_IN_RISKPOOL);
        return risk;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Risk createUpdatedEntity(EntityManager em) {
        Risk risk = new Risk()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .severity(UPDATED_SEVERITY)
            .probability(UPDATED_PROBABILITY)
            .inRiskpool(UPDATED_IN_RISKPOOL);
        return risk;
    }

    @BeforeEach
    public void initTest() {
        risk = createEntity(em);
    }

    @Test
    @Transactional
    public void createRisk() throws Exception {
        int databaseSizeBeforeCreate = riskRepository.findAll().size();

        // Create the Risk
        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risk)))
            .andExpect(status().isCreated());

        // Validate the Risk in the database
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeCreate + 1);
        Risk testRisk = riskList.get(riskList.size() - 1);
        assertThat(testRisk.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRisk.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRisk.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testRisk.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
        assertThat(testRisk.isInRiskpool()).isEqualTo(DEFAULT_IN_RISKPOOL);
    }

    @Test
    @Transactional
    public void createRiskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riskRepository.findAll().size();

        // Create the Risk with an existing ID
        risk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risk)))
            .andExpect(status().isBadRequest());

        // Validate the Risk in the database
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskRepository.findAll().size();
        // set the field null
        risk.setName(null);

        // Create the Risk, which fails.

        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risk)))
            .andExpect(status().isBadRequest());

        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskRepository.findAll().size();
        // set the field null
        risk.setDescription(null);

        // Create the Risk, which fails.

        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risk)))
            .andExpect(status().isBadRequest());

        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInRiskpoolIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskRepository.findAll().size();
        // set the field null
        risk.setInRiskpool(null);

        // Create the Risk, which fails.

        restRiskMockMvc.perform(post("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risk)))
            .andExpect(status().isBadRequest());

        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRisks() throws Exception {
        // Initialize the database
        riskRepository.saveAndFlush(risk);

        // Get all the riskList
        restRiskMockMvc.perform(get("/api/risks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(risk.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY.toString())))
            .andExpect(jsonPath("$.[*].inRiskpool").value(hasItem(DEFAULT_IN_RISKPOOL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRisk() throws Exception {
        // Initialize the database
        riskRepository.saveAndFlush(risk);

        // Get the risk
        restRiskMockMvc.perform(get("/api/risks/{id}", risk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(risk.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY.toString()))
            .andExpect(jsonPath("$.inRiskpool").value(DEFAULT_IN_RISKPOOL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRisk() throws Exception {
        // Get the risk
        restRiskMockMvc.perform(get("/api/risks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisk() throws Exception {
        // Initialize the database
        riskRepository.saveAndFlush(risk);

        int databaseSizeBeforeUpdate = riskRepository.findAll().size();

        // Update the risk
        Risk updatedRisk = riskRepository.findById(risk.getId()).get();
        // Disconnect from session so that the updates on updatedRisk are not directly saved in db
        em.detach(updatedRisk);
        updatedRisk
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .severity(UPDATED_SEVERITY)
            .probability(UPDATED_PROBABILITY)
            .inRiskpool(UPDATED_IN_RISKPOOL);

        restRiskMockMvc.perform(put("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRisk)))
            .andExpect(status().isOk());

        // Validate the Risk in the database
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeUpdate);
        Risk testRisk = riskList.get(riskList.size() - 1);
        assertThat(testRisk.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRisk.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRisk.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testRisk.getProbability()).isEqualTo(UPDATED_PROBABILITY);
        assertThat(testRisk.isInRiskpool()).isEqualTo(UPDATED_IN_RISKPOOL);
    }

    @Test
    @Transactional
    public void updateNonExistingRisk() throws Exception {
        int databaseSizeBeforeUpdate = riskRepository.findAll().size();

        // Create the Risk

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskMockMvc.perform(put("/api/risks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risk)))
            .andExpect(status().isBadRequest());

        // Validate the Risk in the database
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRisk() throws Exception {
        // Initialize the database
        riskRepository.saveAndFlush(risk);

        int databaseSizeBeforeDelete = riskRepository.findAll().size();

        // Delete the risk
        restRiskMockMvc.perform(delete("/api/risks/{id}", risk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Risk> riskList = riskRepository.findAll();
        assertThat(riskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Risk.class);
        Risk risk1 = new Risk();
        risk1.setId(1L);
        Risk risk2 = new Risk();
        risk2.setId(risk1.getId());
        assertThat(risk1).isEqualTo(risk2);
        risk2.setId(2L);
        assertThat(risk1).isNotEqualTo(risk2);
        risk1.setId(null);
        assertThat(risk1).isNotEqualTo(risk2);
    }
}
