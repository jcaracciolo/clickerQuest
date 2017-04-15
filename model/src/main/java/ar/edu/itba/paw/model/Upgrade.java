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
        if(level%3 == 0){
            return 1+ 0.1 *level;
        }

        return 1D;
    }

    public Double getOutputMultiplier(){
        if(level%3 == 1){
            return 1 - 0.1 * level;
        }

        return 1D;
    }

    public Double getCostReduction(){
        if(level%3 == 2) {
            return 1 - 0.1 *level;
        }

        return 1D;
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
