package ar.edu.itba.paw.model;

import java.util.Date;

/**
 * Created by daniel on 4/3/17.
 */
public class Wealth {

    private long userId;
    private int resourceType;
    private int production; // int?
    private int storage; // int?
    private Date lastUpdated;

    public Wealth(long userId, int resourceType, int production, int storage, Date lastUpdated) {
        this.userId = userId;
        this.resourceType = resourceType;
        this.production = production;
        this.storage = storage;
        this.lastUpdated = lastUpdated;
    }

    public long getUserId() {
        return userId;
    }

    public int getResourceType() {
        return resourceType;
    }

    public int getProduction() {
        return production;
    }

    public int getStorage() {
        return storage;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
