package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.RiskResponse;
import com.riskbusters.norisknofun.repository.RiskResponseRepository;
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

import com.riskbusters.norisknofun.domain.enumeration.RiskResponseType;
import com.riskbusters.norisknofun.domain.enumeration.StatusType;
/**
 * Integration tests for the {@link RiskResponseResource} REST controller.
 */
@SpringBootTest(classes = NoRiskNoFunApp.class)
public class RiskResponseResourceIT {

    private static final RiskResponseType DEFAULT_TYPE = RiskResponseType.PREVENTION;
    private static final RiskResponseType UPDATED_TYPE = RiskResponseType.CONTINGENCY;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final StatusType DEFAULT_STATUS = StatusType.TODO;
    private static final StatusType UPDATED_STATUS = StatusType.WIP;

    @Autowired
    private RiskResponseRepository riskResponseRepository;

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

    private MockMvc restRiskResponseMockMvc;

    private RiskResponse riskResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RiskResponseResource riskResponseResource = new RiskResponseResource(riskResponseRepository);
        this.restRiskResponseMockMvc = MockMvcBuilders.standaloneSetup(riskResponseResource)
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
    public static RiskResponse createEntity(EntityManager em) {
        RiskResponse riskResponse = new RiskResponse()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return riskResponse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskResponse createUpdatedEntity(EntityManager em) {
        RiskResponse riskResponse = new RiskResponse()
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return riskResponse;
    }

    @BeforeEach
    public void initTest() {
        riskResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createRiskResponse() throws Exception {
        int databaseSizeBeforeCreate = riskResponseRepository.findAll().size();

        // Create the RiskResponse
        restRiskResponseMockMvc.perform(post("/api/risk-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskResponse)))
            .andExpect(status().isCreated());

        // Validate the RiskResponse in the database
        List<RiskResponse> riskResponseList = riskResponseRepository.findAll();
        assertThat(riskResponseList).hasSize(databaseSizeBeforeCreate + 1);
        RiskResponse testRiskResponse = riskResponseList.get(riskResponseList.size() - 1);
        assertThat(testRiskResponse.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRiskResponse.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRiskResponse.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRiskResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riskResponseRepository.findAll().size();

        // Create the RiskResponse with an existing ID
        riskResponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskResponseMockMvc.perform(post("/api/risk-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskResponse)))
            .andExpect(status().isBadRequest());

        // Validate the RiskResponse in the database
        List<RiskResponse> riskResponseList = riskResponseRepository.findAll();
        assertThat(riskResponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskResponseRepository.findAll().size();
        // set the field null
        riskResponse.setType(null);

        // Create the RiskResponse, which fails.

        restRiskResponseMockMvc.perform(post("/api/risk-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskResponse)))
            .andExpect(status().isBadRequest());

        List<RiskResponse> riskResponseList = riskResponseRepository.findAll();
        assertThat(riskResponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskResponseRepository.findAll().size();
        // set the field null
        riskResponse.setDescription(null);

        // Create the RiskResponse, which fails.

        restRiskResponseMockMvc.perform(post("/api/risk-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskResponse)))
            .andExpect(status().isBadRequest());

        List<RiskResponse> riskResponseList = riskResponseRepository.findAll();
        assertThat(riskResponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskResponseRepository.findAll().size();
        // set the field null
        riskResponse.setStatus(null);

        // Create the RiskResponse, which fails.

        restRiskResponseMockMvc.perform(post("/api/risk-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskResponse)))
            .andExpect(status().isBadRequest());

        List<RiskResponse> riskResponseList = riskResponseRepository.findAll();
        assertThat(riskResponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRiskResponses() throws Exception {
        // Initialize the database
        riskResponseRepository.saveAndFlush(riskResponse);

        // Get all the riskResponseList
        restRiskResponseMockMvc.perform(get("/api/risk-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riskResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getRiskResponse() throws Exception {
        // Initialize the database
        riskResponseRepository.saveAndFlush(riskResponse);

        // Get the riskResponse
        restRiskResponseMockMvc.perform(get("/api/risk-responses/{id}", riskResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(riskResponse.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRiskResponse() throws Exception {
        // Get the riskResponse
        restRiskResponseMockMvc.perform(get("/api/risk-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRiskResponse() throws Exception {
        // Initialize the database
        riskResponseRepository.saveAndFlush(riskResponse);

        int databaseSizeBeforeUpdate = riskResponseRepository.findAll().size();

        // Update the riskResponse
        RiskResponse updatedRiskResponse = riskResponseRepository.findById(riskResponse.getId()).get();
        // Disconnect from session so that the updates on updatedRiskResponse are not directly saved in db
        em.detach(updatedRiskResponse);
        updatedRiskResponse
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restRiskResponseMockMvc.perform(put("/api/risk-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRiskResponse)))
            .andExpect(status().isOk());

        // Validate the RiskResponse in the database
        List<RiskResponse> riskResponseList = riskResponseRepository.findAll();
        assertThat(riskResponseList).hasSize(databaseSizeBeforeUpdate);
        RiskResponse testRiskResponse = riskResponseList.get(riskResponseList.size() - 1);
        assertThat(testRiskResponse.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRiskResponse.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRiskResponse.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRiskResponse() throws Exception {
        int databaseSizeBeforeUpdate = riskResponseRepository.findAll().size();

        // Create the RiskResponse

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskResponseMockMvc.perform(put("/api/risk-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskResponse)))
            .andExpect(status().isBadRequest());

        // Validate the RiskResponse in the database
        List<RiskResponse> riskResponseList = riskResponseRepository.findAll();
        assertThat(riskResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRiskResponse() throws Exception {
        // Initialize the database
        riskResponseRepository.saveAndFlush(riskResponse);

        int databaseSizeBeforeDelete = riskResponseRepository.findAll().size();

        // Delete the riskResponse
        restRiskResponseMockMvc.perform(delete("/api/risk-responses/{id}", riskResponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RiskResponse> riskResponseList = riskResponseRepository.findAll();
        assertThat(riskResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskResponse.class);
        RiskResponse riskResponse1 = new RiskResponse();
        riskResponse1.setId(1L);
        RiskResponse riskResponse2 = new RiskResponse();
        riskResponse2.setId(riskResponse1.getId());
        assertThat(riskResponse1).isEqualTo(riskResponse2);
        riskResponse2.setId(2L);
        assertThat(riskResponse1).isNotEqualTo(riskResponse2);
        riskResponse1.setId(null);
        assertThat(riskResponse1).isNotEqualTo(riskResponse2);
    }
}
