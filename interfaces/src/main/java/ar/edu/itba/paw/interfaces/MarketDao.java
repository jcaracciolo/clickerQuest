package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.StockMarketEntry;

public interface MarketDao {

    boolean registerPurchase(StockMarketEntry stockMarketEntry);
}
