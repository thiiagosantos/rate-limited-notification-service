package service;

import gateway.NotificationGateway;

public class NotificationServiceImpl implements NotificationService {

    private final NotificationGateway gateway;

    public NotificationServiceImpl(NotificationGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void send(NotificationType type, String message, String user) {
        gateway.send(message, user);
    }
}
