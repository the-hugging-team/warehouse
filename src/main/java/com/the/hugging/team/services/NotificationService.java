package com.the.hugging.team.services;

import com.the.hugging.team.entities.Notification;
import com.the.hugging.team.entities.NotificationType;
import com.the.hugging.team.entities.Role;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.repositories.NotificationRepository;

import java.util.List;
import java.util.Set;

public class NotificationService {
    private static NotificationService INSTANCE;
    private final NotificationRepository notificationRepository = NotificationRepository.getInstance();
    private final UserService userService = UserService.getInstance();

    public static NotificationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationService();
        }

        return INSTANCE;
    }

    public void sendNotification(NotificationType type) {
        // TODO: implement
        Set<Role> roles = type.getRoles();
        userService.getUsersByRoles(roles).forEach(user -> {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setNotificationTemplate(type.getNotificationTemplate());
            notificationRepository.save(notification);
        });
    }

    public List<Notification> getAllUserNotifications(User user) {
        return notificationRepository.getAllByUser(user);
    }

    public List<Notification> getUnreadUserNotifications(User user) {
        return notificationRepository.getUnreadByUser(user);
    }
}
