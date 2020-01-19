package com.riskbusters.norisknofun.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.riskbusters.norisknofun.domain.Activity;
import com.riskbusters.norisknofun.domain.DeviceToken;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.ActivityRepository;
import com.riskbusters.norisknofun.repository.DeviceTokenRepository;
import com.riskbusters.norisknofun.service.util.GroupUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for sending notifications with firebase.
 * <p>
 * We use the {@link Async} annotation to send notifications asynchronously.
 */
@Service
public class MessagingService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final ActivityRepository activityRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final MessageSource messageSource;

    public MessagingService(ActivityRepository activityRepository, DeviceTokenRepository deviceTokenRepository, MessageSource messageSource) {
        this.activityRepository = activityRepository;
        this.deviceTokenRepository = deviceTokenRepository;
        this.messageSource = messageSource;
        if(FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp();
        }
    }

    /*
        Example usage:
        User user = userService.getUserWithAuthorities().get();
        Activity activity = new Activity();
        activity.setActivityDescriptionKey("activity.event.addedToProject");
        activity.setTargetUrl("/lol");
        Set<User> users = new HashSet<>();
        users.add(user);
        activity.setUsers(users);
        messagingService.addActivityWithNotification(activity);
     */
    public void addActivityWithNotification(Activity activity) {
        activityRepository.save(activity);

        GroupUserUtil.groupByLang(activity.getUsers()).forEach((langKey, userList) -> {
            String message = messageSource.getMessage(activity.getActivityDescriptionKey(), null, Locale.forLanguageTag(langKey));
            try {
                sendNotification(new HashSet<>(userList), message);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        });

        log.debug("Created Information and send Notification for Activity: {}", activity);
    }

    @Async
    public void sendNotification(Set<User> users, String messageContent) throws FirebaseMessagingException {
        List<String> affectedTokens = getUserDeviceTokens(users);
        if (affectedTokens == null || affectedTokens.isEmpty()) return;

        MulticastMessage message = MulticastMessage.builder()
            .putData("message",messageContent)
            .addAllTokens(getUserDeviceTokens(users))
            .build();
        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
        log.debug("Notifications send succesfully: {}", response.getSuccessCount());
    }

    private List<String> getUserDeviceTokens(Set<User> users) {
        return deviceTokenRepository.findAllByTokenOwnerIn(users).stream()
            .map(DeviceToken::getDeviceToken)
            .collect(Collectors.toList());
    }
}
