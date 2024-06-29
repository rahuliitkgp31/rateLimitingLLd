package org.example.rateLimiting;

import org.example.Request;
import org.springframework.stereotype.Service;

@Service
public class RateLimitingChain {
    private RateLimiting rateLimiting;

    public RateLimitingChain(UserRateLimiting userRateLimiting, DeviceRateLimiting deviceRateLimiting) {
        userRateLimiting.setNextRateLimiter(deviceRateLimiting);
        rateLimiting = userRateLimiting;
    }

    public boolean isRequestAllowed(Request request) {
        return rateLimiting.isRequestAllowed(request);
    }
 }
