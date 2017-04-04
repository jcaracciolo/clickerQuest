package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public class Upgrade {
    private long level;
    private String description;
    private FactoryType factoryType;
    private double cost;

    public Upgrade(long level, String description, FactoryType factoryType, int cost) {
        this.level = level;
        this.description = description;
        this.factoryType = factoryType;
        this.cost = cost;
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

    public static Upgrade getBylevelAndType(int level, FactoryType type){
        return new Upgrade(level,"Upgrade n°" + level,type,300*level);
    }
}
