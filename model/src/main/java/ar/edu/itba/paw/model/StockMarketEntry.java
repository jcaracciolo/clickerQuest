package ar.edu.itba.paw.model;

import java.util.Calendar;

/**
 * Created by juanfra on 09/05/17.
 */
public class StockMarketEntry {

    long time;
    long userid;
    ResourceType resourceType;
    double amount;

    public StockMarketEntry(long time, long userid, ResourceType resourceType, double amount) {
        this.time = time;
        this.userid = userid;
        this.resourceType = resourceType;
        this.amount = amount;
    }

    public StockMarketEntry(long userid, ResourceType resourceType, double amount) {
        this.time = Calendar.getInstance().getTimeInMillis();
        this.userid = userid;
        this.resourceType = resourceType;
        this.amount = amount;
    }

    public long getTime() {
        return time;
    }

    public long getUserid() {
        return userid;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public double getAmount() {
        return amount;
    }
}
