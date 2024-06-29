package org.example;

public class Request {
    private String userId;
    private String deviceId;
    private String locationId;

    public Request(String userId, String deviceId, String locationId) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.locationId = locationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getLocationId() {
        return locationId;
    }
}
