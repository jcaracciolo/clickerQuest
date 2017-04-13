package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.*;
import ar.edu.itba.paw.model.packages.PackageBuilder;

import java.util.Map;

/**
 * Created by juanfra on 03/04/17.
 */
public class Wealth {

    private long userid;
    //Already calculated data, ready for display
    private Storage storage;
    private Productions productions;
    private Map<FactoryType,SingleProduction> singleProductions;

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

    public Map<ResourceType,String> getFormattedStorage(){
        return getStorage().formatted();
    }

    public Map<ResourceType,String> getFormattedProductions(){
        return getProductions().formatted();
    }

    public Wealth purchaseResult(Factory f) {
        if (! f.isBuyable(this)) {
            return null;
        }
        Storage calculatedStorage = storage.getUpdatedStorage(productions);
        Recipe recipe = f.getRecipe();
        FactoryCost cost = f.getCost();
        PackageBuilder<Storage> storageBuilder = Storage.packageBuilder();
        PackageBuilder<Productions> productionsBuilder = Productions.packageBuilder();


        for (ResourceType r: ResourceType.values() ) {
            storageBuilder.putItemWithDate(r,calculatedStorage.getValue(r),calculatedStorage.getLastUpdated(r));
            productionsBuilder.putItem(r,productions.getValue(r));

            if (cost.contains(r)) {
                storageBuilder.addItem(r, -cost.getValue(r));
            }

            if (recipe.contains(r)){
                productionsBuilder.addItem(r,recipe.getValue(r));
            }
        }



        Productions p = productionsBuilder.buildPackage();
        return new Wealth(userid,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
    }
}
