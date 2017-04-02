package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;

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
    private long upgradeID;

    public Factory(long owner, FactoryType type, double amount,
                   double inputReduction, double outputMultiplier, double costReduction, long upgradeID) {
        this.owner = owner;
        this.type = type;
        this.amount = amount;
        this.inputReduction = inputReduction;
        this.outputMultiplier = outputMultiplier;
        this.costReduction = costReduction;
        this.upgradeID = upgradeID;
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

    public double getInputReduction() {
        return inputReduction;
    }

    public double getOutputMultiplier() {
        return outputMultiplier;
    }

    public double getCostReduction() {
        return costReduction;
    }

    public long getUpgradeID() {
        return upgradeID;
    }

    public List<Amount> getCost(){
        return new ArrayList();
    }

    public Upgrade getUpgrade() {
        return new Upgrade(owner,"Super Upgrade",2,300);
    }

    public Recipe getRecipe() {
        return type.getRecipe();
    }

    public String getImage(){
        return "1.img";
    }
}
