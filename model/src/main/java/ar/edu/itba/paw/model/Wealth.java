package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Production;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.packages.SingleFactoryProduction;
import ar.edu.itba.paw.model.packages.Storage;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by juanfra on 03/04/17.
 */
public class Wealth {

    public long userid;
    public Calendar lastUpdated;
    //Already calculated data, ready for display
    public Storage storage;
    public Production productions;
    public Map<FactoryType,SingleFactoryProduction> singleProductions;

    public Wealth(long userid, Calendar lastUpdated, Storage storage, Production productions) {
        this.userid = userid;
        this.lastUpdated = lastUpdated;
        this.storage = storage;
        this.productions = productions;
    }

    public long getUserid() {
        return userid;
    }

    public Calendar getLastUpdated() {
        return lastUpdated;
    }

    public Storage getStorage() {
        return storage.getUpdatedStorage(productions,Calendar.getInstance());
    }

    public Production getProductions() {
        return productions;
    }

    public Map<ResourceType,String> getStoredResources(){
        return storage.getFormatedOutputs();
    }

    public Map<ResourceType,String> getProduction(){
        return productions.getFormatedOutputs();
    }
}
