package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.*;

import java.util.Map;

/**
 * Created by juanfra on 31/03/17.
 */
public class Factory implements Comparable<Factory> {

    private long userid;
    private FactoryType type;
    private double amount;
    private double inputReduction;
    private double outputMultiplier;
    private double costReduction;
    private long level;

    public Factory(long userid, FactoryType type, double amount, double inputReduction, double outputMultiplier, double costReduction, long level) {
        this.userid = userid;
        this.type = type;
        this.amount = amount;
        this.inputReduction = inputReduction;
        this.outputMultiplier = outputMultiplier;
        this.costReduction = costReduction;
        this.level = level;

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
                .calculateRecipe(inputReduction,outputMultiplier,level);
    }

    public FactoryCost getCost() {
        return type.getBaseCost().calculateCost(amount,costReduction);
    }

    public FactoriesProduction getFactoriesProduction() {
        return type.getBaseRecipe().calculateProduction(amount,inputReduction,outputMultiplier,1);
    }

    public String getImage(){
        return getType().getId() + ".jpg";
    }

    public Factory purchaseResult() {
        return new Factory(userid,type,
                amount +1,
                inputReduction,outputMultiplier,costReduction,level);
    }

    public Factory upgradeResult(){
        Upgrade newUpgrade = getNextUpgrade();

        return new Factory(userid,type,amount,
                            inputReduction * newUpgrade.getInputReduction(),
                            outputMultiplier * newUpgrade.getOutputMultiplier(),
                            costReduction * newUpgrade.getCostReduction(),
                            level + 1
        );
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

    public Upgrade getNextUpgrade() {
        return Upgrade.getUpgrade(type,level + 1);
    }

    public int compareTo(Factory f) {
        return this.getType().getId() - f.getType().getId();
    }

    public long getLevel() {
        return level;
    }
}
