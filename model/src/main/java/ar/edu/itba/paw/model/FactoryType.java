package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.BaseCost;
import ar.edu.itba.paw.model.packages.Implementations.FactoryCost;
import ar.edu.itba.paw.model.packages.Implementations.BaseRecipe;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.PackageType;

/**
 * Created by juanfra on 31/03/17.
 */
public enum FactoryType {
    NOTHINGFORWOOD(0),
    WOODFORIRON(1),
    IRONFORGOLD(2),
    WOODFORGOLD(3);

    private int id;
    FactoryType(int i){
        id=i;
    }


    public int getId() {
        return id;
    }

    public static FactoryType fromId(int id){
        for (FactoryType f: FactoryType.values()){
            if(f.getId() == id){
                return f;
            }
        }
        return null;
    }

    public BaseCost getBaseCost(){
        PackageBuilder<BaseCost> costBuilder = PackageType.BaseCostType.packageBuilder();

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
                break;
            case WOODFORGOLD:
                costBuilder.putItem(ResourceType.IRON,100D);
                costBuilder.putItem(ResourceType.WOOD,100D);
                costBuilder.putItem(ResourceType.GOLD,1000D)    ;
                break;
            default:
                throw new RuntimeException("There is no cost for this factory");
        }

        return costBuilder.buildPackage();
    }

    public BaseRecipe getBaseRecipe() {
        PackageBuilder<BaseRecipe> recipeBuilder = PackageType.BaseRecipeType.packageBuilder();

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
