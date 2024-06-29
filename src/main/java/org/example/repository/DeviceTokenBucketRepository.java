package org.example.repository;

import org.example.entities.DeviceTokenBucket;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DeviceTokenBucketRepository {
    private Map<String, DeviceTokenBucket> deviceTokenBucketMap;
    public DeviceTokenBucket getDeviceTokenBucket(String userId) {
        return deviceTokenBucketMap.get(userId);
    }
    public void setDeviceTokenBucket(DeviceTokenBucket deviceTokenBucket) {
        deviceTokenBucketMap.put(deviceTokenBucket.getDeviceId(), deviceTokenBucket);
    }
}
