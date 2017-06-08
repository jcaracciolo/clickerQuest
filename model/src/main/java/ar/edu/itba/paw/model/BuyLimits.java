package ar.edu.itba.paw.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Julian Benitez on 6/7/2017.
 */
public class BuyLimits {
    private FactoryType factoryType;
    private ResourceType limitant;
    private Boolean isProduction;
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
        if(Math.round(Math.floor(deficit)) < maxBuyable){
            maxBuyable = Math.round(Math.floor(deficit));
            limitant = resourceType;
            isProduction = true;
        }
        productionDeficits.put(resourceType,BigDecimal.valueOf(deficit));
    }

    public void addStorageDeficit(ResourceType resourceType, Double deficit){
        if(Math.round(Math.floor(deficit)) < maxBuyable){
            maxBuyable = Math.round(Math.floor(deficit));
            limitant = resourceType;
            isProduction = false;
        }
        storageDeficits.put(resourceType,BigDecimal.valueOf(deficit));
    }

    public ResourceType getLimitant() {
        return limitant;
    }

    public Boolean isProduction() {
        return isProduction;
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

    public FactoryType getFactoryType() {
        return factoryType;
    }
}
