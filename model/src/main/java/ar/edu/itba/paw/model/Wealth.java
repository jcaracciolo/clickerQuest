package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.refactorPackages.Implementations.Productions;
import ar.edu.itba.paw.model.refactorPackages.Implementations.SingleProduction;
import ar.edu.itba.paw.model.refactorPackages.Implementations.Storage;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by juanfra on 03/04/17.
 */
public class Wealth {

    public long userid;
    //Already calculated data, ready for display
    public Storage storage;
    public Productions productions;
    public Map<FactoryType,SingleProduction> singleProductions;

    public Wealth(long userid, Storage storage, Productions productions) {
        this.userid = userid;
        this.storage = storage;
        this.productions = productions;
    }

    public long getUserid() {
        return userid;
    }

    public Storage getStorage() {
        return storage.getUpdatedStorage(productions);
    }

    public Productions getProductions() {
        return productions;
    }

    public Map<ResourceType,String> getStoredResources(){
        return storage.formatted();
    }

    public Map<ResourceType,String> getProduction(){
        return productions.formatted();
    }

    public Wealth purchaseResult(Factory f) {
       // Storage s = storage.purchase(f);
        return new Wealth(userid,storage,productions);
    }
}
