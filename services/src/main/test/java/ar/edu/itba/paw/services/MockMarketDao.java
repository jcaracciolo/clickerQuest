package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.model.StockMarketEntry;

public class MockMarketDao implements MarketDao {

    @Override
    public boolean registerPurchase(StockMarketEntry stockMarketEntry) {
        return true;
    }
}
