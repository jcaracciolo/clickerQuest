package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public class Upgrade {
    private FactoryType factoryType;
    private long level;
    private String description;
    private double cost;



    public Upgrade(FactoryType factoryType, long level, String description, double cost) {
        this.level = level;
        this.description = description;
        this.factoryType = factoryType;
        this.cost = cost;
    }

    public static Upgrade getUpgrade(FactoryType type, long level) {
        return new Upgrade(type, level,"Upgrade n°" + level, 300*level);
    }

    public static Double getInputReduction(long level){
        return 1+ 0.1 *level;
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

}
