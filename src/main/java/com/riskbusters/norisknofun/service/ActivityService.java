package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.Activity;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.ActivityRepository;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;

/*
 * Service for handling and transforming activities.
 */
@Service
public class ActivityService {

    private MessageSource messageSource;
    private ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository, MessageSource messageSource) {
        this.activityRepository = activityRepository;
        this.messageSource = messageSource;
    }

    public Page<Activity> getTranslatedActivityPage(Pageable page, User user) {
        Locale langKey = Locale.forLanguageTag(user.getLangKey());
        Page<Activity> activities = activityRepository.findAllByUsersIsContaining(page, user).orElseThrow(NoActivitiesFoundException::new);
        activities.forEach(activity -> {
            activity.setActivityDescriptionKey(messageSource.getMessage(activity.getActivityDescriptionKey(), null, langKey));
        });
        return activities;
    }
}
