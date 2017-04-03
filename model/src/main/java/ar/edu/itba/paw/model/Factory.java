package ar.edu.itba.paw.model;

import java.util.Map;

/**
 * Created by juanfra on 31/03/17.
 */
public class Factory {

    private long owner;
    private FactoryType type;
    private double amount;
    private double inputReduction;
    private double outputMultiplier;
    private double costReduction;
    private long level;

    public Factory(long owner, FactoryType type, double amount,
                   double inputReduction, double outputMultiplier, double costReduction, long upgradeID) {
        this.owner = owner;
        this.type = type;
        this.amount = amount;
        this.inputReduction = inputReduction;
        this.outputMultiplier = outputMultiplier;
        this.costReduction = costReduction;
        this.level = upgradeID;
    }

    public long getOwner() {
        return owner;
    }

    public FactoryType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    //PROCESS WITH MULTIPLIERS
    public Map<Resources,Double> getCost() { return type.getCost(); }

    //PROCESS WITH MULTIPLIERS
    public Map<Resources,Double> getRecipe() { return type.getRecipe(); }

    public long getLevel() {
        return level;
    }

    public String getImage(){
        return "1.img";
    }
}
