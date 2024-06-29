package org.example.rateLimiting;

import org.example.utils.Constants;
import org.example.Request;
import org.example.entities.DeviceTokenBucket;
import org.example.repository.DeviceTokenBucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DeviceRateLimiting extends RateLimiting {

    @Autowired
    DeviceTokenBucketRepository deviceTokenBucketRepository;

    @Override
    public boolean isRequestAllowed(Request request) {
        String deviceId = request.getDeviceId();

        DeviceTokenBucket deviceTokenBucket =
                Optional.ofNullable(deviceTokenBucketRepository.getDeviceTokenBucket(deviceId))
                        .map(deviceExistingTokenBucket -> {
                            updateBucket(deviceExistingTokenBucket);
                            return deviceExistingTokenBucket;
                        })
                        .orElseGet(() -> createNewDeviceTokenBucket(deviceId));

        return Optional.ofNullable(deviceTokenBucket)
                .filter(bucket -> bucket.getCurrTokens() > 0)
                .map(bucket -> {
                    bucket.setCurrTokens(bucket.getCurrTokens() - 1);
                    deviceTokenBucketRepository.setDeviceTokenBucket(bucket);
                    return Objects.isNull(nextRateLimiting) || nextRateLimiting.isRequestAllowed(request);
                })
                .orElse(false);

    }

    public DeviceTokenBucket createNewDeviceTokenBucket(String deviceId) {
        DeviceTokenBucket deviceTokenBucket = new DeviceTokenBucket(deviceId,
                System.currentTimeMillis(), Constants.REFILL_RATE,
                Constants.INITIAL_TOKENS, Constants.MAX_TOKENS);
        deviceTokenBucketRepository.setDeviceTokenBucket(deviceTokenBucket);
        return deviceTokenBucket;
    }

    public void updateBucket(DeviceTokenBucket deviceTokenBucket) {
        int oldTokens = deviceTokenBucket.getCurrTokens();
        long lastReqTime = deviceTokenBucket.getLastReqTime();
        long currTime = System.currentTimeMillis();
        double refillRatePerMs = deviceTokenBucket.getRefillRatePerSec()/1000.0;
        int newTokens = (int) (oldTokens+(currTime-lastReqTime)*refillRatePerMs);
        newTokens = Math.max(newTokens, deviceTokenBucket.getMaxTokensAllowed());
        deviceTokenBucket.setCurrTokens(newTokens);
        deviceTokenBucket.setLastReqTime(currTime);
    }

    public void setNextRateLimiter(RateLimiting rateLimiting) {
        nextRateLimiting=rateLimiting;
    }

}
