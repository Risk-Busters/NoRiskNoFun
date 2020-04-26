package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.*;
import com.riskbusters.norisknofun.domain.achievements.Achievement;
import com.riskbusters.norisknofun.domain.enumeration.ProbabilityType;
import com.riskbusters.norisknofun.domain.enumeration.RiskResponseType;
import com.riskbusters.norisknofun.domain.enumeration.SeverityType;
import com.riskbusters.norisknofun.domain.enumeration.StatusType;
import com.riskbusters.norisknofun.repository.ProjectRisksBaseRepository;
import com.riskbusters.norisknofun.repository.RiskRepository;
import com.riskbusters.norisknofun.repository.RiskResponseRepository;
import com.riskbusters.norisknofun.repository.gamification.UserGamificationRepository;
import com.riskbusters.norisknofun.web.rest.UserResourceIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = NoRiskNoFunApp.class)
class ProjectRiskServiceTestIT {

    private final String PROPOSED_RISK_TITLE = "TITLE";
    private final String PROPOSED_RISK_DESC = "DESC";

    private Project demoProject;
    private User demoUser;
    private User demoUserTwo;
    private User demoUserThree;
    private User demoUserFour;
    private Set<RiskDiscussion> demoDiscussions;

    @Autowired
    private RiskRepository riskRepository;

    @Autowired
    private ProjectRisksBaseRepository projectRisksBaseRepository;

    @Autowired
    private RiskResponseRepository riskResponseRepository;

    @Autowired
    private UserGamificationRepository gamificationRepository;

    @Autowired
    private ProjectRiskService projectRiskService;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {
        demoUser = UserResourceIT.createEntity(em);
        demoUserTwo = UserResourceIT.createEntity(em);
        demoUserThree = UserResourceIT.createEntity(em);
        demoUserFour = UserResourceIT.createEntity(em);

        demoProject = new Project()
            .addUser(demoUser)
            .addUser(demoUserTwo)
            .addUser(demoUserThree)
            .addUser(demoUserFour)
            .description("Demo Project Desc")
            .name("Demo Project Name")
            .start(LocalDate.of(2000, 1, 1))
            .end(LocalDate.of(3000, 1, 1));

        em.persist(demoUser);
        em.persist(demoUserTwo);
        em.persist(demoUserThree);
        em.persist(demoUserFour);
        em.persist(demoProject);

        addGamification(demoUser);
        addGamification(demoUserTwo);
        addGamification(demoUserThree);
        addGamification(demoUserFour);
    }

    private void addGamification(User user) {
        UserGamification gamification = new UserGamification();
        gamification.setUser(user);
        gamification.setUserAchievements(new HashSet<Achievement>());
        gamificationRepository.save(gamification);
    }

    @Test
    @Transactional
    void proposeProjectRisk() {
        int riskRepositorySize = riskRepository.findAll().size();
        int projectRiskRepositorySize = projectRisksBaseRepository.findAll().size();

        ProjectRisks projectRisk = projectRiskService.proposeProjectRisk(PROPOSED_RISK_TITLE,PROPOSED_RISK_DESC, demoProject, demoUser);

        assertEquals(riskRepository.findAll().size(), riskRepositorySize+1);
        assertEquals(projectRisksBaseRepository.findAll().size(), projectRiskRepositorySize+1);

        assertEquals(projectRisk.getRisk().getName(), PROPOSED_RISK_TITLE);
        assertEquals(projectRisk.getRisk().getDescription(), PROPOSED_RISK_DESC);
        assertEquals("proposed", projectRisk.riskDiscussionStatus);
        assertEquals(projectRisk.getProject(), demoProject);
    }

    // Should cover the most scenarios
   @Test
    @Transactional
    void riskDiscussion() {
        ProjectRisks projectRisk = projectRiskService.proposeProjectRisk(PROPOSED_RISK_TITLE,PROPOSED_RISK_DESC, demoProject, demoUser);
        assertEquals("proposed", projectRisk.riskDiscussionStatus);

        for (int i = 0; i < 10; i++) {
            projectRisk = projectRiskService.addLikeToProposedProjectRisk(projectRisk, demoUser);
        }
        assertEquals("toBeDiscussed", projectRisk.riskDiscussionStatus);

        projectRiskService.saveProjectRiskDiscussion(SeverityType.OK, ProbabilityType.MAYBE, projectRisk, demoUser);
        projectRiskService.saveProjectRiskDiscussion(SeverityType.OK, ProbabilityType.MAYBE, projectRisk, demoUserTwo);
        projectRiskService.saveProjectRiskDiscussion(SeverityType.OK, ProbabilityType.MAYBE, projectRisk, demoUserThree);
        projectRiskService.saveProjectRiskDiscussion(SeverityType.OK, ProbabilityType.MAYBE, projectRisk, demoUserFour);

        projectRisk = projectRiskService.addPersonInCharge(projectRisk, demoUser);

        for (int i = 0; i < 10; i++) {
            RiskResponse riskResponse = new RiskResponse();
            riskResponse.setDescription("Demo Dec");
            riskResponse.setStatus(StatusType.DONE);
            riskResponse.setType(RiskResponseType.MITIGATION);
            riskResponseRepository.saveAndFlush(riskResponse);

            projectRisk.addRiskResponse(riskResponse);
        }

        projectRiskService.updateDiscussionStatus(projectRisk);
        projectRisksBaseRepository.findById(projectRisk.getId()).ifPresentOrElse(projectRisks -> {
            assertEquals("final", projectRisks.riskDiscussionStatus);
        }, () -> {
            fail("Project Risk went missing during the discussion process.");
        });

    }

}
