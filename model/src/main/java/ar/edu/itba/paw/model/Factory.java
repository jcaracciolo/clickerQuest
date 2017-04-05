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

    private Upgrade upgrade;

    public Factory(long owner, FactoryType type, double amount, double inputReduction, double outputMultiplier, double costReduction, long level, Upgrade upgrade) {
        this.owner = owner;
        this.type = type;
        this.amount = amount;
        this.inputReduction = inputReduction;
        this.outputMultiplier = outputMultiplier;
        this.costReduction = costReduction;
        this.level = level;
        this.upgrade = upgrade;
    }
    public Factory(long owner, FactoryType type, double amount, double inputReduction, double outputMultiplier, double costReduction, long level) {
        this(owner,type,amount,inputReduction,outputMultiplier,costReduction,level,null);
    }

    public Factory(Factory f, Upgrade upgrade) {
        this.owner = f.owner;
        this.type = f.type;
        this.amount = f.amount;
        this.inputReduction = f.inputReduction;
        this.outputMultiplier = f.outputMultiplier;
        this.costReduction = f.costReduction;
        this.level = f.level;
        this.upgrade = upgrade;
    }

    //PROCESS WITH MULTIPLIERS
    public ResourcePackage getCost() { return type.getCost(); }

    //PROCESS WITH MULTIPLIERS
    public ResourcePackage getRecipe() {
        return type.getRecipe().applyMultipliers(amount,inputReduction,outputMultiplier);
    }
    public String getImage(){
        return "1.img";
    }

    public double getAmount() {
        return amount;
    }
}
