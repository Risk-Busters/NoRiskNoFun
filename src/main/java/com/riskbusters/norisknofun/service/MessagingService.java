package com.riskbusters.norisknofun.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.riskbusters.norisknofun.domain.Activity;
import com.riskbusters.norisknofun.domain.DeviceToken;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.ActivityRepository;
import com.riskbusters.norisknofun.repository.DeviceTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessagingService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final ActivityRepository activityRepository;
    private final DeviceTokenRepository deviceTokenRepository;

    public MessagingService(ActivityRepository activityRepository, DeviceTokenRepository deviceTokenRepository) {
        this.activityRepository = activityRepository;
        this.deviceTokenRepository = deviceTokenRepository;
    }

    public void addActivityWithNotification(Activity activity) throws FirebaseMessagingException {
        activityRepository.save(activity);
        sendNotification(activity.getUsers(), activity.getDescription());
        log.debug("Created Information and send Notification for Activity: {}", activity);
    }

    public void addActivityWithNotification(Activity activity, String notificationMessage) throws FirebaseMessagingException {
        activityRepository.save(activity);
        sendNotification(activity.getUsers(), notificationMessage);
        log.debug("Created Information and send Notification for Activity: {}", activity);
    }

    @Async
    public void sendNotification(Set<User> users, String messageContent) throws FirebaseMessagingException {
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
