package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public class Upgrade {
    private FactoryType factoryType;
    private long level;
    private String description;
    private double cost;

    private Upgrade(FactoryType factoryType, long level, String description, double cost) {
        this.level = level;
        this.description = description;
        this.factoryType = factoryType;
        this.cost = cost;
    }

    public static Upgrade getUpgrade(FactoryType type, long level) {
        if(level<=0) return null;
        return new Upgrade(type, level,"Upgrade n°" + level, 300*level);
    }

    public Double getInputReduction(){
        long localLevel = (level+2)/3;
        if(factoryType.getBaseRecipe().getInputs().isEmpty()){
            return 1D;
        }
        return Math.max(Math.exp(-0.07*localLevel), 0.5D);
    }

    public Double getOutputMultiplier(){
        long localLevel = factoryType.getBaseRecipe().getInputs().isEmpty()? (level+1)/2 : (level+1)/3;
        return 1+localLevel/20D;
    }

    public Double getCostReduction(){
        long localLevel = factoryType.getBaseRecipe().getInputs().isEmpty() ? level/2 : level/3;
        return Math.max(Math.exp(-0.07 * localLevel), 0.5D);
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

    public long getLevel() {
        return level;
    }

    public FactoryType getFactoryId() {
        return factoryType;
    }

    public boolean isBuyable(Wealth w) {
        return cost <= w.getStorage().getValue(ResourceType.MONEY);
    }



}
