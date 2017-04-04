package ar.edu.itba.paw.model;

import java.util.*;

/**
 * Created by juanfra on 31/03/17.
 */
public enum FactoryType {
    NOTHINGFORWOOD,
    WOODFORIRON,
    IRONFORGOLD,
    WOODFORGOLD;

    public ResourcePackage getRecipe() {
        Random rng = new Random();
        ResourcePackageBuilder recipeBuilder = new ResourcePackageBuilder();
        //TODO implement correct factories
        Double wood = rng.nextDouble()*5;
        Double iron = rng.nextDouble()*5;
        Double gold = rng.nextDouble()*5;

        switch (this) {
            case NOTHINGFORWOOD:
                recipeBuilder.addItem(Resources.WOOD,wood);
                break;
            case WOODFORIRON:
                recipeBuilder.addItem(Resources.WOOD,-wood);
                recipeBuilder.addItem(Resources.IRON,iron);
                break;
            case IRONFORGOLD:
                recipeBuilder.addItem(Resources.IRON,-iron);
                recipeBuilder.addItem(Resources.GOLD,gold);
                break;
            case WOODFORGOLD:
                recipeBuilder.addItem(Resources.WOOD,-wood);
                recipeBuilder.addItem(Resources.GOLD,gold);
                break;
            default:
                throw new RuntimeException("There is no ResourcePackage for this factory");
        }

        return recipeBuilder.buildPackage();
    }

    public int getId() {
        switch (this){
            case NOTHINGFORWOOD: return 0;
            case WOODFORIRON: return 1;
            case IRONFORGOLD: return 2;
            case WOODFORGOLD: return 3;
            default: throw new RuntimeException("There is no ID for the corresponding FactoryType");
        }
    }

    public ResourcePackage getCost() {
        ResourcePackageBuilder costBuilder = new ResourcePackageBuilder();

        switch (this) {
            case NOTHINGFORWOOD:
                costBuilder.addItem(Resources.WOOD,10D);
                break;
            case WOODFORIRON:
                costBuilder.addItem(Resources.WOOD,100D);
                break;
            case IRONFORGOLD:
                costBuilder.addItem(Resources.WOOD,100D);
                costBuilder.addItem(Resources.IRON,100D);
            case WOODFORGOLD:
                costBuilder.addItem(Resources.IRON,100D);
                costBuilder.addItem(Resources.WOOD,100D);
                costBuilder.addItem(Resources.GOLD,1000D);
                break;
            default:
                throw new RuntimeException("There is no ResourcePackage for this factory");
        }
        return costBuilder.buildPackage();
    }

    public static FactoryType getById(int id){
        for (FactoryType f: FactoryType.values()){
            if(f.getId() == id){
                return f;
            }
        }

        return null;
    }

}
