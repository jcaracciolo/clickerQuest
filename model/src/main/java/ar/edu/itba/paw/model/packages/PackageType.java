package ar.edu.itba.paw.model.packages;


import ar.edu.itba.paw.model.packages.Implementations.*;

/**
 * Created by juanfra on 08/04/17.
 */
public enum PackageType {
    FactoryCostType,RecipeType,StorageType,ProductionType,SingleProductionType;

    public PackageBuilder packageBuilder(){
        switch (this){
            case FactoryCostType:
                return new PackageBuilder(FactoryCost.VALIDATOR,FactoryCost.CREATOR);

            case RecipeType:
                return new PackageBuilder(Recipe.VALIDATOR,Recipe.CREATOR);

            case StorageType:
                return new PackageBuilder(Storage.VALIDATOR,Storage.CREATOR);

            case ProductionType:
                return new PackageBuilder(Productions.VALIDATOR,Productions.CREATOR);

            case SingleProductionType:
                return new PackageBuilder(SingleProduction.VALIDATOR,SingleProduction.CREATOR);
            default:
                throw new RuntimeException("There is no PackageBuilder for the given PackageType" + this.name());
        }
    }
}
