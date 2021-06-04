package com.rateLimiter.ratelimitersolution.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RATE_LIMIT_CONFIGURATION")
public class RateLimitConfiguration {

    @Id
    private int id;

    private String userName;

    private int hitsInWindow;

    private int windowSize;

    private String windowSizeTimeUnit;

    private String apiName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHitsInWindow() {
        return hitsInWindow;
    }

    public void setHitsInWindow(int hitsInWindow) {
        this.hitsInWindow = hitsInWindow;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public String getWindowSizeTimeUnit() {
        return windowSizeTimeUnit;
    }

    public void setWindowSizeTimeUnit(String windowSizeTimeUnit) {
        this.windowSizeTimeUnit = windowSizeTimeUnit;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
