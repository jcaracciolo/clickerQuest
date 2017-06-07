package ar.edu.itba.paw.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Julian Benitez on 6/7/2017.
 */
public class BuyLimits {
    private FactoryType factoryType;
    //The max amount of factories of FactoryType that the user could buy if the productions stayed the same
    private Long maxBuyable=Long.MAX_VALUE;
    //This is the max amount of factories the user could buy if the resource in question is the only limit
    private Map<ResourceType, BigDecimal> productionDeficits;
    private Map<ResourceType, BigDecimal> storageDeficits;

    public BuyLimits(FactoryType factoryType) {
        this.factoryType = factoryType;
        productionDeficits = new HashMap<>();
        storageDeficits = new HashMap<>();
    }

    public void addProductionDeficit(ResourceType resourceType, Double deficit){
        maxBuyable = Math.round(Math.floor(Math.min(deficit,maxBuyable*1.0)));
        productionDeficits.put(resourceType,BigDecimal.valueOf(deficit));
    }

    public void addStorageDeficit(ResourceType resourceType, Double deficit){
        maxBuyable = Math.round(Math.floor(Math.min(deficit,maxBuyable*1.0)));
        storageDeficits.put(resourceType,BigDecimal.valueOf(deficit));
    }

    public Map<ResourceType, BigDecimal> getProductionsDeficit(){
        return productionDeficits;
    }

    public Map<ResourceType, BigDecimal> getStorageDeficits() {
        return storageDeficits;
    }

    public Long getMaxFactories(){
        return maxBuyable;
    }
}
