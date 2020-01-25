package com.riskbusters.norisknofun.domain.achievements;

import com.riskbusters.norisknofun.domain.enumeration.AchievementType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AchievementTest {

    @Test
    void getProjectMemberName() {
        ProjectMember projectMember = new ProjectMember();
        assertEquals(AchievementType.PROJECT_MEMBER, projectMember.getName());
    }

    @Test
    void getProjectManager() {
        ProjectManager projectManager = new ProjectManager();
        assertEquals( AchievementType.PROJECT_MANAGER, projectManager.getName());
    }

    @Test
    void getRiskBusterName() {
        RiskBuster riskBuster = new RiskBuster();
        assertEquals(AchievementType.RISK_BUSTER, riskBuster.getName());
    }

    @Test
    void getRiskMasterName() {
        RiskMaster riskMaster = new RiskMaster();
        assertEquals(AchievementType.RISK_MASTER, riskMaster.getName());
    }

    @Test
    void getRiskOwnerName() {
        RiskOwner riskOwner = new RiskOwner();
        assertEquals(AchievementType.RISK_OWNER, riskOwner.getName());
    }

    @Test
    void getRiskSageName() {
        RiskSage riskSage = new RiskSage();
        assertEquals(AchievementType.RISK_SAGE, riskSage.getName());
    }
}
