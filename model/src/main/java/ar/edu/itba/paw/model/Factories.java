package ar.edu.itba.paw.model;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by juanfra on 31/03/17.
 */
public enum Factories {
    NOTHINGFORWOOD, WOODFORIRON, IRONFORGOLD, WOODFORGOLD;

    public Recipe getRecipe() {
        Random rng = new Random();
        List<Production> input = new ArrayList<>();
        List<Production> output = new ArrayList<>();
        //TODO implement correct factories
        Production wood = new Production(Resources.WOOD,rng.nextDouble()*5);
        Production iron = new Production(Resources.WOOD,rng.nextDouble()*5);
        Production gold = new Production(Resources.WOOD,rng.nextDouble()*5);

        switch (this) {
            case NOTHINGFORWOOD:
                output.add(wood);
                break;
            case WOODFORIRON:
                input.add(wood);
                output.add(iron);
                break;
            case IRONFORGOLD:
                input.add(iron);
                output.add(gold);
            case WOODFORGOLD:
                input.add(wood);
                output.add(gold);
                break;
            default:
                throw new InvalidStateException("There is no Recipe for this factory");
        }

        return new Recipe(input,output);
    }

    public long getId() {
        switch (this){
            case NOTHINGFORWOOD: return 0;
            case WOODFORIRON: return 1;
            case IRONFORGOLD: return 2;
            case WOODFORGOLD: return 3;
        }
    }

    public static Factories getById(long id){
        for (Factories f:Factories.values()){
            if(f.getId() == id){
                return f;
            }
        }

        return null;
    }

}
