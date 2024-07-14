package service;

import service.exception.RateLimitedException;

public interface NotificationService {

    void send(NotificationType type, String message, String user) throws RateLimitedException;

}
