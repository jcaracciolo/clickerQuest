package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public class Upgrade {
    private long id;
    private String description;
    private int factoryId;
    private double cost;

    public Upgrade(long id, String description, int factoryId, int cost) {
        this.id = id;
        this.description = description;
        this.factoryId = factoryId;
        this.cost = cost;
    }

    public String toString() {
        return description;
    }
    
    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public long getId() {
        return id;
    }

    public int getFactoryId() {
        return factoryId;
    }
}
