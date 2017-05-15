package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.*;
import ar.edu.itba.paw.model.packages.PackageBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

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
        if(!f.isUpgreadable(this)) {
            return null;
        }

        Upgrade u = f.getNextUpgrade();
        FactoriesProduction factoriesProduction = f.getFactoriesProduction();

        Storage calculatedStorage = getStorage();
        PackageBuilder<Storage> storageBuilder = Storage.packageBuilder();
        PackageBuilder<Productions> productionsBuilder = Productions.packageBuilder();

        calculatedStorage.rawMap().forEach(storageBuilder::putItem);
        calculatedStorage.getLastUpdated().forEach(storageBuilder::setLastUpdated);
        storageBuilder.addItem(ResourceType.MONEY,-u.getCost());

        productions.rawMap().forEach(productionsBuilder::putItem);
        Map<ResourceType,Double> factoryInputs = f.getFactoriesProduction().getInputs();
        Map<ResourceType,Double> factoryOutputs = f.getFactoriesProduction().getOutputs();


        if (u.getInputReduction() != 1) {
            factoriesProduction.getInputs().forEach(
                    (r, d) -> {
                        productionsBuilder.addItem(r,d);
                        productionsBuilder.addItem(r, - ( factoryInputs.get(r) * u.getInputReduction()));
                    }
            );
        }

        if (u.getOutputMultiplier() != 1) {
            factoriesProduction.getOutputs().forEach(
                    (r, d) -> {
                        productionsBuilder.addItem(r, -d);
                        productionsBuilder.addItem(r,  factoryOutputs.get(r) * u.getOutputMultiplier());
                    }
            );
        }

        return new Wealth(userid,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
    }

    public Wealth calculateProductions(Collection<Factory> factories) {
        if(factories == null ){
            return null;
        }
        PackageBuilder<Productions> productionsBuilder = Productions.packageBuilder();
        Arrays.stream(ResourceType.values()).forEach((r) -> productionsBuilder.putItem(r,0D));
        factories.stream()
                .filter(f -> f.getAmount()>0)
                .map(factory -> factory.getFactoriesProduction()).
                forEach(factoriesProduction -> {
                            factoriesProduction.getOutputs().keySet().stream().
                                    forEach(res -> productionsBuilder.addItem(res, factoriesProduction.getOutputs().get(res)));
                            factoriesProduction.getInputs().keySet().stream().
                                    forEach(res -> productionsBuilder.addItem(res, -factoriesProduction.getInputs().get(res)));
                        }
                );

        return new Wealth(userid,storage,productionsBuilder.buildPackage());
    }

    public Wealth addResource(ResourceType resource, double amount) {
        Storage calculatedStorage = getStorage();
        PackageBuilder<Storage> storageBuilder = Storage.packageBuilder();

        calculatedStorage.rawMap().forEach(storageBuilder::putItem);
        calculatedStorage.getLastUpdated().forEach(storageBuilder::setLastUpdated);
        storageBuilder.addItem(resource,amount);
        return new Wealth(userid,storageBuilder.buildPackage(),productions);
    }
}
