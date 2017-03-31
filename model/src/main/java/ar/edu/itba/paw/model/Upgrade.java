package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public class Upgrade {
    private long id;
    private String description;
    private int factoryId;
    private int cost;
    private double inputDivider;
    private double outputMultiplier;
    private double inputReduction;
    private double outputReduction;


    public Upgrade(int id, String description, int factoryId, int cost, double inputDivider, double outputMultiplier, double inputReduction, double outputReduction) {
        this.id = id;
        this.description = description;
        this.factoryId = factoryId;
        this.cost = cost;
        this.inputDivider = inputDivider;
        this.outputMultiplier = outputMultiplier;
        this.inputReduction = inputReduction;
        this.outputReduction = outputReduction;
    }

    public String toString() {
        return description;
    }


    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public long getId() {
        return id;
    }

    public int getFactoryId() {
        return factoryId;
    }

    public double getInputDivider() {
        return inputDivider;
    }

    public double getOutputMultiplier() {
        return outputMultiplier;
    }

    public double getInputReduction() {
        return inputReduction;
    }

    public double getOutputReduction() {
        return outputReduction;
    }
}
