package org.example.rateLimiting;

import org.example.utils.Constants;
import org.example.Request;
import org.example.entities.UserTokenBucket;
import org.example.repository.UserTokenBucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserRateLimiting extends RateLimiting{

    @Autowired
    private UserTokenBucketRepository userTokenBucketRepository;

    @Override
    public boolean isRequestAllowed(Request request) {
        String userId = request.getUserId();

        UserTokenBucket userTokenBucket =
                Optional.ofNullable(userTokenBucketRepository.getUserTokenBucket(userId))
                .map(userExistingTokenBucket -> {
                    updateBucket(userExistingTokenBucket);
                    return userExistingTokenBucket;
                })
                .orElseGet(() -> createNewUserTokenBucket(userId));

        return Optional.ofNullable(userTokenBucket)
                .filter(bucket -> bucket.getCurrTokens() > 0)
                .map(bucket -> {
                    bucket.setCurrTokens(bucket.getCurrTokens() - 1);
                    userTokenBucketRepository.setUserTokenBucket(bucket);
                    return Objects.isNull(nextRateLimiting) || nextRateLimiting.isRequestAllowed(request);
                })
                .orElse(false);

    }

    public UserTokenBucket createNewUserTokenBucket(String userId) {
        UserTokenBucket userTokenBucket = new UserTokenBucket(userId,
                System.currentTimeMillis(), Constants.REFILL_RATE,
                Constants.INITIAL_TOKENS, Constants.MAX_TOKENS);
        userTokenBucketRepository.setUserTokenBucket(userTokenBucket);
        return userTokenBucket;
    }

    public void updateBucket(UserTokenBucket userTokenBucket) {
        int oldTokens = userTokenBucket.getCurrTokens();
        long lastReqTime = userTokenBucket.getLastReqTime();
        long currTime = System.currentTimeMillis();
        double refillRatePerMs = userTokenBucket.getRefillRatePerSec()/1000.0;
        int newTokens = (int) (oldTokens+(currTime-lastReqTime)*refillRatePerMs);
        newTokens = Math.max(newTokens, userTokenBucket.getMaxTokensAllowed());
        userTokenBucket.setCurrTokens(newTokens);
        userTokenBucket.setLastReqTime(currTime);
    }

    public void setNextRateLimiter(RateLimiting rateLimiting) {
        nextRateLimiting=rateLimiting;
    }

}
