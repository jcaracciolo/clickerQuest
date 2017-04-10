package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.*;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.PackageType;

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
        if (! f.isBuyable(this)) {
            return null;
        }
        Storage calculatedStorage = storage.getUpdatedStorage(productions);
        Recipe recipe = f.getType().getRecipe();
        FactoryCost cost = f.getType().getCost();
        PackageBuilder<Storage> storageBuilder = PackageType.StorageType.packageBuilder();
        PackageBuilder<Productions> productionsBuilder = PackageType.ProductionType.packageBuilder();


        for (ResourceType r: ResourceType.values() ) {
            storageBuilder.putItemWithDate(r,calculatedStorage.getValue(r),calculatedStorage.getLastUpdated(r));
            productionsBuilder.putItem(r,productions.getValue(r));

            if (cost.contains(r)) {
                storageBuilder.addItem(r, -cost.getValue(r));
            }

            if (recipe.contains(r)){
                productionsBuilder.addItem(r,f.getType().getRecipe().getValue(r));
            }
        }


        return new Wealth(userid,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
    }
}
