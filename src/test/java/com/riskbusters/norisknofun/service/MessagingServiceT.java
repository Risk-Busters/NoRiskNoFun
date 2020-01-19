package com.riskbusters.norisknofun.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.Activity;
import com.riskbusters.norisknofun.domain.DeviceToken;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.ActivityRepository;
import com.riskbusters.norisknofun.repository.DeviceTokenRepository;
import com.riskbusters.norisknofun.web.rest.ActivityResourceIT;
import com.riskbusters.norisknofun.web.rest.UserResourceIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(classes = NoRiskNoFunApp.class)
class MessagingServiceT {

    private static final String DEFAULT_TOKEN = "IAM-EXAMPLE-TOKEN-ONE";
    private static final String DEFAULT_TOKEN_TWO = "IAM-EXAMPLE-TOKEN-TWO";

    @Autowired
    private DeviceTokenRepository deviceTokenRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntityManager em;

    private DeviceToken deviceToken;
    private DeviceToken deviceTokenTwo;
    private User user;
    private Activity activity;
    private Set<User> userSet;

    @BeforeEach
    public void init() {
        final MessagingService messagingService = new MessagingService(activityRepository, deviceTokenRepository, messageSource);
        deviceToken = new DeviceToken();
        deviceTokenTwo = new DeviceToken();

        user = UserResourceIT.createEntity(em);
        em.persist(user);
        userSet = new HashSet<>();
        userSet.add(user);

        deviceToken.setDeviceToken(DEFAULT_TOKEN);
        deviceToken.setTokenOwner(user);
        deviceTokenTwo.setDeviceToken(DEFAULT_TOKEN_TWO);
        deviceTokenTwo.setTokenOwner(user);
        em.persist(deviceToken);
        em.persist(deviceTokenTwo);
        deviceTokenRepository.saveAndFlush(deviceToken);
        deviceTokenRepository.saveAndFlush(deviceTokenTwo);

        activity = ActivityResourceIT.createEntity(em);
        activity.setUsers(userSet);
    }

    @Test
    @Transactional
    void addActivityWithNotification() throws FirebaseMessagingException {
        int databaseSize = activityRepository.findAll().size();

        // Initialize database
        messagingService.addActivityWithNotification(activity);
        activityRepository.flush();

        int databaseSizeAfterAdd = activityRepository.findAll().size();
        assertThat(databaseSizeAfterAdd).isEqualTo(databaseSize+1);
        // TODO: Check how to test FCM
    }

    @Test
    @Transactional
    void testAddActivityWithNotificationWithCustomMessage() {
        // TODO: Check how to test FCM
    }

    // TODO: Test upcoming token lifecycle methods
}
