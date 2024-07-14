package service;

import gateway.NotificationGateway;
import service.exception.RateLimitedException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationServiceImpl implements NotificationService {

    private final Map<String, Map<NotificationType, List<Instant>>> notificationsSent = new HashMap<>();
    private final HashMap<NotificationType, RateConfig> rateLimits;
    private final NotificationGateway gateway;

    public NotificationServiceImpl(NotificationGateway gateway) {
        this.gateway = gateway;
        this.rateLimits = new HashMap<>();

        this.rateLimits.put(NotificationType.STATUS, new RateConfig(2, 60)); // 1 minute
        this.rateLimits.put(NotificationType.NEWS, new RateConfig(1, 86400)); // 1 day
        this.rateLimits.put(NotificationType.MARKETING, new RateConfig(3, 3600)); // 1 hour
    }

    @Override
    public void send(NotificationType type, String message, String user) throws RateLimitedException {
        if (shouldLimit(type, user)) throw new RateLimitedException();

        gateway.send(message, user);
        recordNotification(type, user);
    }

    private boolean shouldLimit(NotificationType type, String user) {
        if (!notificationsSent.containsKey(user)) return false;

        RateConfig limit = rateLimits.get(type);
        Instant now = Instant.now();
        List<Instant> notificationsForUser = notificationsSent
                .computeIfAbsent(user, i -> new HashMap<>())
                .computeIfAbsent(type, o -> new ArrayList<>());

        Instant windowStart = now.minusSeconds(limit.timeWindowInSeconds);
        notificationsForUser.removeIf(time -> time.isBefore(windowStart));

        return notificationsForUser.size() >= limit.maxRequests;
    }

    private void recordNotification(NotificationType type, String user) {
        List<Instant> notifications = notificationsSent
                .computeIfAbsent(user, k -> new HashMap<>())
                .computeIfAbsent(type, k -> new ArrayList<>());
        notifications.add(Instant.now());
    }
}
