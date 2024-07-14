# Rate-Limited Notification Service

## Overview

The Rate-Limited Notification Service is designed to simulate notifications to users while enforcing rate limits for different types of notifications. This ensures that users do not receive an excessive number of notifications within a given time window.

## Features

- **Rate Limiting**: Limits the number of notifications a user can receive based on the type of notification.
- **Notification Types**: Supports various notification types such as STATUS, NEWS, and MARKETING.
- **Extensible Design**: Easily extendable to add new notification types and rate limit configurations.

## Notification Types and Rate Limits

The application supports three types of notifications, each with its own rate limit configuration:

- **STATUS**: A user can receive up to 2 status notifications per minute.
- **NEWS**: A user can receive 1 news notification per day.
- **MARKETING**: A user can receive up to 3 marketing notifications per hour.

## Implementation Details

### Classes and Interfaces

- **NotificationService**: An interface defining the contract for sending notifications.
- **NotificationServiceImpl**: The implementation of the `NotificationService` interface, which includes rate limiting logic.
- **NotificationGateway**: A class representing the external notification sending mechanism.
- **RateConfig**: A class encapsulating the rate limit configuration (maximum requests and time window) for each notification type.
- **NotificationType**: An enum representing the different types of notifications (STATUS, NEWS, MARKETING).
- **RateLimitedException**: A custom exception thrown when a rate limit is exceeded.

### Rate Limiting Logic

1. **Initialization**:
    - Rate limits for each notification type are configured in the constructor of `NotificationServiceImpl`.
    - `notificationsSent` is a nested map that keeps track of sent notifications, with the outer map keyed by user and the inner map keyed by notification type.

2. **Sending Notifications**:
    - The `send` method checks if sending a notification should be rate limited using the `shouldLimit` method.
    - If the rate limit is exceeded, a `RateLimitedException` is thrown.
    - If not, the notification is sent via the `NotificationGateway`, and the notification is recorded.

3. **Rate Limiting Check**:
    - The `shouldLimit` method checks if the number of notifications sent within the specified time window exceeds the allowed maximum for the notification type.
    - Old notifications outside the current time window are removed from the list.

4. **Recording Notifications**:
    - The `recordNotification` method adds a timestamp of the current notification to the list of sent notifications.

### Example Usage

```java
NotificationGateway gateway = new SomeNotificationGatewayImplementation();
NotificationService notificationService = new NotificationServiceImpl(gateway);

try {
    notificationService.send(NotificationType.STATUS, "Your order has been shipped", "user123");
} catch (RateLimitedException e) {
    System.out.println("Notification rate limit exceeded");
} 
```
## Building and Running Tests

Ensure you have Maven installed. Run the following command to clean and install the project, including running tests:

```sh
mvn clean install
```
## Contact
For any questions, please contact me at `thiilima.santos@gmail.com`