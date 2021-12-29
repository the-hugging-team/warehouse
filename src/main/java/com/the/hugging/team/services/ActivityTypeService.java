package com.the.hugging.team.services;

import com.the.hugging.team.entities.ActivityType;
import com.the.hugging.team.repositories.ActivityTypeRepository;

public class ActivityTypeService {
    private static final ActivityTypeRepository activityTypeRepository = ActivityTypeRepository.getInstance();
    private static ActivityTypeService INSTANCE = null;

    public static ActivityTypeService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityTypeService();
        }

        return INSTANCE;
    }

    public ActivityType getActivityTypeBySlug(String slug) {
        return activityTypeRepository.getBySlug(slug).orElse(null);
    }
}
