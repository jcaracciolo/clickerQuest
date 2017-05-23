package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.*;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created by juanfra on 03/04/17.
 */
public class Wealth {

    private final long userid;
    private final Storage storage;
    private final Productions productions;

    public Wealth(long userid, @NotNull  Storage storage, @NotNull Productions productions) {
        if ( !storage.getResources().equals(productions.getResources()) ) {
            throw new IllegalArgumentException("Storage and Productions must have same resources");
        }
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

    public Wealth purchaseResult(@NotNull  Factory f) {
        if (!f.isBuyable(this)) {
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

    public Wealth upgradeResult(@NotNull Factory f) {

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

    public Wealth calculateProductions(@NotNull  Collection<Factory> factories) {
        PackageBuilder<Productions> productionsBuilder = Productions.packageBuilder();
        Arrays.stream(ResourceType.values()).forEach((r) -> productionsBuilder.putItem(r,0D));
        factories.stream()
                .filter(f -> f.getAmount()>0)
                .map(Factory::getFactoriesProduction)
                .map(FactoriesProduction::getOutputs)
                .forEach((m) -> m.forEach(productionsBuilder::addItem));

        factories.stream()
                .filter(f -> f.getAmount()>0)
                .map(Factory::getFactoriesProduction)
                .map(FactoriesProduction::getInputs)
                .forEach((m) -> m.forEach(
                        (r,d) -> productionsBuilder.addItem(r,-d)));

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

    public double calculateScore() {
        double score = productions.rawMap().entrySet().stream()
                .map((e) -> e.getKey().getPrice() * e.getValue())
                .reduce((d1,d2) -> d1+d2)
                .orElse(0D);

        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wealth wealth = (Wealth) o;

        if( !getStorage().equals(wealth.getStorage())) {
            return false;
        }

        if ( !productions.equals(wealth.getProductions())) {
            return false;
        }

        return userid == wealth.userid;

    }

    @Override
    public int hashCode() {
        return (int) (userid ^ (userid >>> 32));
    }
}
