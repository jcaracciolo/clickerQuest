package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;

import java.util.Collection;

/**
 * Created by juanfra on 23/03/17.
 */
public interface MarketDao {

    boolean registerPurchase(StockMarketEntry stockMarketEntry);
}
