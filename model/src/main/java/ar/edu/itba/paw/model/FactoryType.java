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

    public Map<Resources,Double> getRecipe() {
        Random rng = new Random();
        Map<Resources,Double> recipe = new HashMap<>();
        //TODO implement correct factories
        Double wood = rng.nextDouble()*5;
        Double iron = rng.nextDouble()*5;
        Double gold = rng.nextDouble()*5;

        switch (this) {
            case NOTHINGFORWOOD:
                recipe.put(Resources.WOOD,wood);
                break;
            case WOODFORIRON:
                recipe.put(Resources.WOOD,-wood);
                recipe.put(Resources.IRON,iron);
                break;
            case IRONFORGOLD:
                recipe.put(Resources.IRON,-iron);
                recipe.put(Resources.GOLD,gold);
            case WOODFORGOLD:
                recipe.put(Resources.WOOD,-wood);
                recipe.put(Resources.GOLD,gold);
                break;
            default:
                throw new RuntimeException("There is no ResourcePackage for this factory");
        }

        return recipe;
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

    public Map<Resources,Double> getCost() { return null; }

    public static FactoryType getById(int id){
        for (FactoryType f: FactoryType.values()){
            if(f.getId() == id){
                return f;
            }
        }

        return null;
    }

}
