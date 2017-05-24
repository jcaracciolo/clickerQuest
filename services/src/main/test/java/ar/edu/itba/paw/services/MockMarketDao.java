package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.StockMarketEntry;

import java.util.Map;

public class MockMarketDao implements MarketDao {

    @Override
    public boolean registerPurchase(StockMarketEntry stockMarketEntry) {
        return true;
    }

    @Override
    public Map<ResourceType, Double> getPopularities() {
        return null;
    }
}
