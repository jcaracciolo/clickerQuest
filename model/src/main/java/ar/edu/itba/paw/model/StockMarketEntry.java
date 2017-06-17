package ar.edu.itba.paw.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Table(name = "stockmarket")
@Entity
public class StockMarketEntry {
    @Transient
    private ResourceType resourceType;

    @Id
    @Column(name = "resourcetype")
    private int _resourcetype;

    private double amount;

    @PostLoad
    private void postLoad() {
        resourceType = ResourceType.fromId(_resourcetype);
    }

    public StockMarketEntry(){}

    public StockMarketEntry(@NotNull ResourceType resourceType, double amount) {
        this.resourceType = resourceType;
        this.amount = amount;
        _resourcetype = resourceType.getId();
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
