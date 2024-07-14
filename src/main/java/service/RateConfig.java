package service;

public class RateConfig {

    final int maxRequests;
    final int timeWindowInSeconds;

    public RateConfig(int maxRequests, int timeWindowInSeconds) {
        this.maxRequests = maxRequests;
        this.timeWindowInSeconds = timeWindowInSeconds;
    }
}
