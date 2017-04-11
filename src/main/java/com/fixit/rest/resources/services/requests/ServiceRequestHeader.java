package com.fixit.rest.resources.services.requests;


/**
 * Created by Kostyantin on 3/20/2017.
 */

public class ServiceRequestHeader {

    public String userId;
    public String latestScreen;

    public ServiceRequestHeader() { }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLatestScreen() {
        return latestScreen;
    }

    public void setLatestScreen(String latestScreen) {
        this.latestScreen = latestScreen;
    }

    @Override
    public String toString() {
        return "APIRequestHeader{" +
                "userId='" + userId + '\'' +
                ", latestScreen='" + latestScreen + '\'' +
                '}';
    }
}
