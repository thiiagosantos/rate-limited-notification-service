package service;

public interface NotificationService {

    void send(NotificationType type, String message, String user);

}
