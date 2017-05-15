package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.model.StockMarketEntry;

/**
 * Created by daniel on 5/15/17.
 */
public class MockMarketDao implements MarketDao {

    @Override
    public boolean registerPurchase(StockMarketEntry stockMarketEntry) {
        return true;
    }
}
