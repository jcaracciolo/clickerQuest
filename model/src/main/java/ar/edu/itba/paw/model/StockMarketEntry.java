package ar.edu.itba.paw.model;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class StockMarketEntry {

    private ResourceType resourceType;
    private double amount;

    public StockMarketEntry(@NotNull ResourceType resourceType, double amount) {
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
