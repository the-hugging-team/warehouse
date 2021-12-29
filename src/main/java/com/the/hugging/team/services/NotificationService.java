package com.the.hugging.team.services;

import com.the.hugging.team.entities.Notification;
import com.the.hugging.team.entities.User;

import java.util.List;

public class NotificationService {
    private static NotificationService INSTANCE;

    public static NotificationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationService();
        }

        return INSTANCE;
    }

    public void sendNotification() { //, NotificationType type) {
        // TODO: implement
        // get roles for notification type
        // get users with those roles
        // send notification to users
        // get notification template from type
    }

    public List<Notification> getAllUserNotifications(User user) {
        // TODO: implement
        return null;
    }

    public List<Notification> getUnreadUserNotifications(User user) {
        // TODO: implement
        return null;
    }
}
