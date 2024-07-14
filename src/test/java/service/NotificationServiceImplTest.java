package service;

import gateway.NotificationGateway;
import org.junit.jupiter.api.Test;
import service.exception.RateLimitedException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NotificationServiceImplTest {

    NotificationGateway gateway = new NotificationGateway();
    NotificationService service = new NotificationServiceImpl(gateway);

    @Test
    void shouldSendAllNotificationsWithinLimits() {
        service.send(NotificationType.STATUS, "this is the firts status", "user1");
        service.send(NotificationType.STATUS, "this is the second status", "user1");
        service.send(NotificationType.NEWS, "this is the daily news", "user1");
        service.send(NotificationType.MARKETING, "contract me?", "user1");
        service.send(NotificationType.MARKETING, "contract me, the code is working good", "user1");
        service.send(NotificationType.MARKETING, "I'll be happy working for u", "user1");
    }

    @Test
    void shouldRateLimitsForStatusNotifications() {
        // successful ones
        service.send(NotificationType.STATUS, "this is the firts status", "user1");
        service.send(NotificationType.STATUS, "this is the second status", "user1");
        service.send(NotificationType.STATUS, "this is the firts status for user 2", "user1");
        service.send(NotificationType.STATUS, "this is the second status for user 2", "user1");

        // should be rate limited
        assertThrows(RateLimitedException.class, () -> service.send(NotificationType.STATUS, "this one should be rate limited", "user1"));
    }

    @Test
    void shouldRateLimitsForNewsNotifications() {
        // successful ones
        service.send(NotificationType.NEWS, "this is the daily news", "user1");
        service.send(NotificationType.NEWS, "this is the daily news", "user2");

        // should be rate limited
        assertThrows(RateLimitedException.class, () -> service.send(NotificationType.NEWS, "this one should be rate limited", "user1"));
    }

    @Test
    void shouldRateLimitsForMarketingNotifications() {
        // successful ones
        service.send(NotificationType.MARKETING, "contract me?", "user1");
        service.send(NotificationType.MARKETING, "contract me, the code is working good", "user1");
        service.send(NotificationType.MARKETING, "I'll be happy working for u", "user1");

        // should be rate limited
        assertThrows(RateLimitedException.class, () -> service.send(NotificationType.MARKETING, "Bad marketing is not allowed", "user1"));
    }


}