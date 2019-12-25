package com.riskbusters.norisknofun.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.riskbusters.norisknofun.NoRiskNoFunApp;
import com.riskbusters.norisknofun.domain.Activity;
import com.riskbusters.norisknofun.domain.DeviceToken;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.ActivityRepository;
import com.riskbusters.norisknofun.repository.DeviceTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(classes = NoRiskNoFunApp.class)
class MessagingServiceT {

    private static final String DEFAULT_TOKEN = "IAM-EXAMPLE-TOKEN-ONE";
    private static final String DEFAULT_TOKEN_TWO = "IAM-EXAMPLE-TOKEN-TWO";
    private static final String DEFAULT_ACTIVITY_DESCRIPTION = "JUNIT TEST ONE";
    private static final String DEFAULT_ACTIVITY_URL = "/DIDSTUFF/1";

    @Autowired
    private DeviceTokenRepository deviceTokenRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MessagingService messagingService;

    private DeviceToken deviceToken;
    private DeviceToken deviceTokenTwo;
    private User user;
    private Activity activity;
    private  Set<User> userSet;

    @BeforeEach
    public void init() {
        final MessagingService messagingService = new MessagingService(activityRepository, deviceTokenRepository);
        deviceToken = new DeviceToken();
        deviceTokenTwo = new DeviceToken();

        user = new User();
        user.setFirstName("Jon");
        userSet = new HashSet<>();
        userSet.add(user);

        deviceToken.setDeviceToken(DEFAULT_TOKEN);
        deviceToken.setTokenOwner(user);

        deviceTokenTwo.setDeviceToken(DEFAULT_TOKEN_TWO);
        deviceTokenTwo.setTokenOwner(user);

        activity = new Activity();
        activity.setDescription(DEFAULT_ACTIVITY_DESCRIPTION);
        activity.setTargetUrl(DEFAULT_ACTIVITY_URL);
        activity.setUsers(userSet);
    }

    @Test
    void addActivityWithNotification() throws FirebaseMessagingException {
        int databaseSize = activityRepository.findAll().size();

        // Initialize database
        messagingService.addActivityWithNotification(activity);

        int databaseSizeAfterAdd = activityRepository.findAll().size();
        assertThat(databaseSizeAfterAdd).isEqualTo(databaseSize+1);
        // TODO: Check how to test FCM
    }

    @Test
    void testAddActivityWithNotificationWithCustomMessage() {
        // TODO: Check how to test FCM
    }

    // TODO: Test upcoming token lifecycle methods
}
