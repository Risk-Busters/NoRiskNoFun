package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.Activity;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.ActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Set;

public class ActivityService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Async
    public void addActivity(Activity activity) {
        activityRepository.save(activity);
        log.debug("Created Information for Activity: {}", activity);
    }

    @Async
    public void addActivityWithNotification(Activity activity) {
        activityRepository.save(activity);
        // TODO: Add messaging to user here

        log.debug("Created Information and send Notification for Activity: {}", activity);
    }

    @Async
    public void addActivityWithNotification(Activity activity, String notificationMessage) {
        activityRepository.save(activity);
        // TODO: Add messaging to user here
        
        log.debug("Created Information and send Notification for Activity: {}", activity);
    }
}
