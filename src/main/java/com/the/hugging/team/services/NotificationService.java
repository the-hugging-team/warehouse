package com.the.hugging.team.services;

import com.the.hugging.team.entities.Notification;
import com.the.hugging.team.entities.NotificationType;
import com.the.hugging.team.entities.Role;
import com.the.hugging.team.entities.User;
import com.the.hugging.team.repositories.NotificationRepository;
import com.the.hugging.team.repositories.NotificationTypeRepository;

import java.util.List;
import java.util.Set;

public class NotificationService {
    private static NotificationService INSTANCE;
    private final NotificationRepository notificationRepository = NotificationRepository.getInstance();
    private final NotificationTypeRepository notificationTypeRepository = NotificationTypeRepository.getInstance();
    private final UserService userService = UserService.getInstance();

    public static NotificationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationService();
        }

        return INSTANCE;
    }

    public void sendNotification(NotificationType type, String value) {
        Set<Role> roles = type.getRoles();
        userService.getUsersByRoles(roles).forEach(user -> {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setNotification(buildNotificationText(type, value));
            notificationRepository.save(notification);
        });
    }

    private String buildNotificationText(NotificationType type, String value) {
        String propertyToReplace = getPropertyToReplace(type);

        return type.getNotificationTemplate().getTemplate().replace("{{" + propertyToReplace + "}}", value);
    }

    private String getPropertyToReplace(NotificationType type) {
        return switch (type.getSlug()) {
            case NotificationType.PRODUCT_OUT_OF_STOCK, NotificationType.PRODUCT_REACHED_MIN -> "product_name";
            case NotificationType.CASH_REGISTER_OUT_OF_MONEY, NotificationType.CASH_REGISTER_REACHED_MIN -> "cash_register_id";
            default -> "";
        };
    }

    public List<Notification> getAllUserNotifications(User user) {
        return notificationRepository.getAllByUser(user);
    }

    public List<Notification> getUnreadUserNotifications(User user) {
        return notificationRepository.getUnreadByUser(user);
    }

    public NotificationType getNotificationTypeBySlug(String slug) {
        return notificationTypeRepository.getNotificationTypeBySlug(slug).orElse(null);
    }
}
