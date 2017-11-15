package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.model.packages.Implementations.BaseCost;
import ar.edu.itba.paw.model.packages.Implementations.FactoryCost;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BuyLimits {

    private Factory factory;
    private Wealth wealth;

    private FactoryCost cost1;
    private FactoryCost cost10;
    private FactoryCost cost100;
    private FactoryCost costMax;
    private long maxFactories;

    public BuyLimits(Wealth wealth, Factory factory) {
        this.factory = factory;
        this.wealth = wealth;

        cost1 = factory.getCost(1);
        cost10 = factory.getCost(10);
        cost100 = factory.getCost(100);
        maxFactories = calculateMax();

        if(!factory.isBuyable(wealth, maxFactories) || factory.isBuyable(wealth, maxFactories+1)) {
            //TODO delete this before deploying
            throw new IllegalStateException("WRONG CALCULUS OF MAX FACTORY");
        }

        if(maxFactories>0) {
            costMax = factory.getCost(maxFactories);
        }
    }

    public long calculateMax() {
        return Stream.concat(
                factory.getType().getBaseCost().getBaseCost()
                    .entrySet().stream().map((e) -> factory.maxFactoriesLimitedByStorage(e.getKey(), e.getValue(), wealth)),
                factory.getRecipe().getInputs()
                        .entrySet().stream().map((e)-> factory.maxFactoriesLimitedByProduction(e.getKey(),e.getValue(),wealth))
        )
        .min(Double::compare)
        .map(Double::longValue).orElse(0L);
    }

    public FactoryCost getCost1() {
        return cost1;
    }

    public FactoryCost getCost10() {
        return cost10;
    }

    public FactoryCost getCost100() {
        return cost100;
    }

    public FactoryCost getCostMax() {
        return costMax;
    }

    public long getMaxFactories() {
        return maxFactories;
    }
}
