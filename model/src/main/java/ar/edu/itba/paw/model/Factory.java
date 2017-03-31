package ar.edu.itba.paw.model;

import java.util.List;

/**
 * Created by juanfra on 31/03/17.
 */
public class Factory {
    private Factories type;
    private List<Resources> cost;
    private double money;
    private Upgrade upgrade;

    public Factory(Factories type, List<Resources> cost, double money, Upgrade upgrade) {
        this.type = type;
        this.cost = cost;
        this.money = money;
        this.upgrade = upgrade;
    }

    public Factories getType() {
        return type;
    }

    public double getMoney() {
        return money;
    }

    public List<Resources> getCost() {
        return cost;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }
}
