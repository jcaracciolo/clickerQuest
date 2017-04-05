package ar.edu.itba.paw.model;

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
