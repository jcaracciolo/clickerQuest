package ar.edu.itba.paw.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by juanfra on 03/04/17.
 */
public class Wealth {

    public long owner;
    public Date lastUpdated;
    //Already calculated data, ready for display
    public ResourcePackage storage;
    public ResourcePackage productions;

    public Wealth(long owner, Date lastUpdated, ResourcePackage storage, ResourcePackage productions) {
        this.owner = owner;
        this.lastUpdated = lastUpdated;
        this.storage = storage;
        this.productions = productions;
    }

    public Map<Resources,String> getStoredResources(){
        return storage.getFormatedOutputs();
    }

    public Map<Resources,String> getProduction(){
        return productions.getFormatedOutputs();
    }
}
