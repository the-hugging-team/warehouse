package com.the.hugging.team.services;

import com.the.hugging.team.entities.Activity;
import com.the.hugging.team.entities.ActivityType;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.repositories.ActivityRepository;
import com.the.hugging.team.utils.Session;

import java.sql.Timestamp;
import java.util.List;

public class ActivityService {
    private static ActivityService INSTANCE = null;
    private final ActivityRepository activityRepository = ActivityRepository.getInstance();
    private final Session session = Session.getInstance();

    public static ActivityService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityService();
        }

        return INSTANCE;
    }

    public void addActivity(ActivityType activityType)
    {
        Activity activity = new Activity();
        activity.setActivityType(activityType);
        activity.setUser(session.getUser());
        activity.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        activityRepository.save(activity);
    }

    public List<Activity> getActivitiesByUser(User user)
    {
        return activityRepository.getByUser(user);
    }
}
