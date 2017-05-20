package ar.edu.itba.paw.model;

import java.util.Calendar;

public class StockMarketEntry {

    private ResourceType resourceType;
    private double amount;

    public StockMarketEntry(ResourceType resourceType, double amount) {
        this.resourceType = resourceType;
        this.amount = amount;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public double getAmount() {
        return amount;
    }
}
