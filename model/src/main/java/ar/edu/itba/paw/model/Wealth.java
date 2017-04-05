package ar.edu.itba.paw.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by juanfra on 03/04/17.
 */
public class Wealth {

    public long userid;
    public Date lastUpdated;
    //Already calculated data, ready for display
    public ResourcePackage storage;
    public ResourcePackage productions;

    public Wealth(long userid, Date lastUpdated, ResourcePackage storage, ResourcePackage productions) {
        this.userid = userid;
        this.lastUpdated = lastUpdated;
        this.storage = storage;
        this.productions = productions;
    }

    public ResourcePackage getStorage() {
        return storage;
    }

    public ResourcePackage getProductions() {
        return productions;
    }

    public Map<ResourceType,String> getStoredResources(){
        return storage.getFormatedOutputs();
    }

    public Map<ResourceType,String> getProduction(){
        return productions.getFormatedOutputs();
    }
}
