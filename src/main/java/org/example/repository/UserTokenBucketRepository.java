package org.example.repository;

import org.example.entities.UserTokenBucket;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserTokenBucketRepository {
    private Map<String, UserTokenBucket> userTokenBucketMap;
    public UserTokenBucket getUserTokenBucket(String userId) {
        return userTokenBucketMap.get(userId);
    }
    public void setUserTokenBucket(UserTokenBucket userTokenBucket) {
        userTokenBucketMap.put(userTokenBucket.getUserId(), userTokenBucket);
    }
}
