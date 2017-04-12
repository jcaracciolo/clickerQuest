package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.*;

import java.util.Map;

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

    public Recipe getRecipe() {
        return  type.getBaseRecipe()
                .calculateRecipe(inputReduction,outputMultiplier,upgrade.getLevel());
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }

    public FactoryCost getCost() {
        return type.getBaseCost().calculateCost(amount,costReduction);
    }

    public SingleProduction getSingleProduction() {
        return type.getBaseRecipe().calculateProduction(amount,inputReduction,outputMultiplier,1);
    }

    public String getImage(){
        return getType().getId() + ".jpg";
    }

    public Factory purchaseResult() {
        return new Factory(userid,type,
                amount +1,
                inputReduction,outputMultiplier,costReduction,upgrade);
    }

    public boolean isBuyable(Wealth w) {
        FactoryCost cost = getCost();
        Storage storage = w.getStorage();

        for (ResourceType r: cost.getResources()) {
            if(cost.getValue(r) > storage.getValue(r)){
                return false;
            }
        }

        Map<ResourceType,Double> need = type.getBaseRecipe().getInputs();
        Productions productions = w.getProductions();

        for (ResourceType r: need.keySet()) {
            if(need.get(r) > productions.getValue(r)){
                return false;
            }
        }

        return true;
    }
}
