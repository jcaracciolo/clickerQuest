package ar.edu.itba.paw.model.packages;


import ar.edu.itba.paw.model.packages.Implementations.*;

/**
 * Created by juanfra on 08/04/17.
 */
public enum PackageType {
    FactoryCostType{
        public PackageBuilder<FactoryCost> packageBuilder() {
            return new PackageBuilder(FactoryCost.VALIDATOR,FactoryCost.CREATOR);
        }
    },

    BaseRecipeType {
        public PackageBuilder<BaseRecipe> packageBuilder() {
            return new PackageBuilder(BaseRecipe.VALIDATOR, BaseRecipe.CREATOR);
        }
    },

    StorageType{
        public PackageBuilder<Storage> packageBuilder() {
            return new PackageBuilder(Storage.VALIDATOR,Storage.CREATOR);
        }
    },

    ProductionType{
        public PackageBuilder<Productions> packageBuilder() {
            return new PackageBuilder(Productions.VALIDATOR,Productions.CREATOR);
        }
    },

    SingleProductionType{
        public PackageBuilder<SingleProduction> packageBuilder() {
            return new PackageBuilder(SingleProduction.VALIDATOR,SingleProduction.CREATOR);
        }
    },

    RecipeType{
        public PackageBuilder<Recipe> packageBuilder() {
            return new PackageBuilder(Recipe.VALIDATOR,Recipe.CREATOR);
        }
    },

    BaseCostType{
        public PackageBuilder<BaseCost> packageBuilder() {
            return new PackageBuilder(BaseCost.VALIDATOR,BaseCost.CREATOR);
        }
    };

    public abstract PackageBuilder packageBuilder();

}
