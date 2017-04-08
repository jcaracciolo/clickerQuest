package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.refactorPackages.Implementations.FactoryCost;
import ar.edu.itba.paw.model.refactorPackages.Implementations.Recipe;
import ar.edu.itba.paw.model.refactorPackages.PackageBuilder;

import java.util.*;

/**
 * Created by juanfra on 31/03/17.
 */
public enum FactoryType {
    NOTHINGFORWOOD,
    WOODFORIRON,
    IRONFORGOLD,
    WOODFORGOLD;

    public int getId() {
        switch (this){
            case NOTHINGFORWOOD: return 0;
            case WOODFORIRON: return 1;
            case IRONFORGOLD: return 2;
            case WOODFORGOLD: return 3;
            default: throw new RuntimeException("There is no ID for the corresponding FactoryType");
        }
    }

    public static FactoryType fromId(int id){
        for (FactoryType f: FactoryType.values()){
            if(f.getId() == id){
                return f;
            }
        }
        return null;
    }

    public FactoryCost getCost(){
        PackageBuilder<FactoryCost> costBuilder = new PackageBuilder<>(FactoryCost.VALIDATOR,FactoryCost.CREATOR);

        switch (this) {
            case NOTHINGFORWOOD:
                costBuilder.putItem(ResourceType.WOOD,10D);
                break;
            case WOODFORIRON:
                costBuilder.putItem(ResourceType.WOOD,100D);
                break;
            case IRONFORGOLD:
                costBuilder.putItem(ResourceType.WOOD,100D);
                costBuilder.putItem(ResourceType.IRON,100D);
            case WOODFORGOLD:
                costBuilder.putItem(ResourceType.IRON,100D);
                costBuilder.putItem(ResourceType.WOOD,100D);
                costBuilder.putItem(ResourceType.GOLD,1000D);
                break;
            default:
                throw new RuntimeException("There is no cost for this factory");
        }

        return costBuilder.buildPackage();
    }

    public Recipe getRecipe() {
        Random rng = new Random();
        PackageBuilder<Recipe> recipeBuilder = new PackageBuilder<>(Recipe.VALIDATOR,Recipe.CREATOR);

        switch (this) {
            case NOTHINGFORWOOD:
                recipeBuilder.putItem(ResourceType.WOOD, 3D);
                break;
            case WOODFORIRON:
                recipeBuilder.putItem(ResourceType.WOOD, -3D);
                recipeBuilder.putItem(ResourceType.IRON, 1D);
                break;
            case IRONFORGOLD:
                recipeBuilder.putItem(ResourceType.IRON, -2D);
                recipeBuilder.putItem(ResourceType.GOLD, 1D);
                break;
            case WOODFORGOLD:
                recipeBuilder.putItem(ResourceType.WOOD, -10D);
                recipeBuilder.putItem(ResourceType.GOLD, 1D);
                break;
            default:
                throw new RuntimeException("There is no ResourcePackage for this factory");
        }
        return recipeBuilder.buildPackage();
    }
}
