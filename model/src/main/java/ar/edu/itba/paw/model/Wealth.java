package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.*;
import ar.edu.itba.paw.model.packages.PackageBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by juanfra on 03/04/17.
 */
public class Wealth {

    private long userid;
    //Already calculated data, ready for display
    private Storage storage;
    private Productions productions;

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
        if (f == null || !f.isBuyable(this)) {
            return null;
        }
        Storage calculatedStorage = getStorage();
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

        return new Wealth(userid,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
    }

    public Wealth upgradeResult(Factory f) {
        if(f == null ){
            return null;
        }

        Upgrade u = f.getNextUpgrade();
        if(!u.isBuyable(this)) {
            return null;
        }

        SingleProduction singleProduction = f.getSingleProduction();

        Storage calculatedStorage = getStorage();
        PackageBuilder<Storage> storageBuilder = Storage.packageBuilder();
        PackageBuilder<Productions> productionsBuilder = Productions.packageBuilder();

        calculatedStorage.rawMap().forEach(storageBuilder::putItem);
        calculatedStorage.getLastUpdated().forEach(storageBuilder::setLastUpdated);
        storageBuilder.addItem(ResourceType.MONEY,-u.getCost());

        if (u.getInputReduction() != 1) {
            singleProduction.getInputs().forEach(
                        (r, d) -> productionsBuilder.addItem(r, (d * (1 - u.getInputReduction())))
                );
        }

            if (u.getOutputReduction() != 1) {
                singleProduction.getOutputs().forEach(
                        (r, d) -> productionsBuilder.addItem(r, -(d * (1 - u.getOutputReduction())))
                );
            }

        return new Wealth(userid,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
    }

}
