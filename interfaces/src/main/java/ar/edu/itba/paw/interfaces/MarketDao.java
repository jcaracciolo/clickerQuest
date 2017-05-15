package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.StockMarketEntry;

/**
 * Created by juanfra on 23/03/17.
 */
public interface MarketDao {

    boolean registerPurchase(StockMarketEntry stockMarketEntry);
}
