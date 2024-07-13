import gateway.NotificationGateway;
import service.NotificationService;
import service.NotificationServiceImpl;
import service.NotificationType;

public class Main {
    public static void main(String[] args) {
        NotificationService service = new NotificationServiceImpl(new NotificationGateway());

        // basic test cases
        service.send(NotificationType.NEWS, "news 1", "user");
        service.send(NotificationType.NEWS, "news 2", "user");
        service.send(NotificationType.NEWS, "news 3", "user");
        service.send(NotificationType.NEWS, "news 1", "another user");
        service.send(NotificationType.STATUS, "update 1", "user");
    }
}
