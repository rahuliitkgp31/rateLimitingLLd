package org.example.rateLimiting;

import org.example.Request;

public abstract class RateLimiting {
    RateLimiting nextRateLimiting;
    boolean isRequestAllowed(Request request) {
        return false;
    }
    void setNext(RateLimiting rateLimiting) {
        nextRateLimiting = rateLimiting;
    }

}
