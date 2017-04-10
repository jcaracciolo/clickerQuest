package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.FactoryCost;
import ar.edu.itba.paw.model.packages.Implementations.SingleProduction;

/**
 * Created by juanfra on 31/03/17.
 */
public class Factory {

    private long userid;
    private FactoryType type;
    private double amount;
    private double inputReduction;
    private double outputMultiplier;
    private double costReduction;
    private Upgrade upgrade;

    public Factory(long userid, FactoryType type, double amount, double inputReduction, double outputMultiplier, double costReduction, Upgrade upgrade) {
        this.userid = userid;
        this.type = type;
        this.amount = amount;
        this.inputReduction = inputReduction;
        this.outputMultiplier = outputMultiplier;
        this.costReduction = costReduction;
        this.upgrade = upgrade;
    }

    public long getUserid() {
        return userid;
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

    public Upgrade getUpgrade() {
        return upgrade;
    }

    public FactoryCost getCost() {
        return type.getCost().applyMultipliers(amount,costReduction);
    }

    public SingleProduction getSingleProduction() {
        return type.getRecipe().applyMultipliers(amount,inputReduction,outputMultiplier,1);
    }

    public String getImage(){
        return "factory_icon.png";
    }

    public Factory purchaseResult() {
        return new Factory(userid,type,
                amount +1,
                inputReduction,outputMultiplier,costReduction,upgrade);
    }

    public boolean isBuyable(Wealth w) {
        //TODO
        return true;
    }
}
