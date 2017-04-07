package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.ResourceType;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * Created by jubenitez on 05/04/17.
 */
public class Recipe implements Package{
    private ResourcePackage resourcePackage;

    public Recipe(FactoryType factoryType) {
        this.resourcePackage = getRecipeResourcePackage(factoryType);
    }

    public SingleFactoryProduction applyMultipliers(double amount, double inputReduction, double outputMultiplier, int level)
    {
        ResourcePackageBuilder builder = new ResourcePackageBuilder();
        if(amount > 0) {
            for (ResourceType res : resourcePackage.getResources()){
                Double value = resourcePackage.getValue(res);
                if(value > 0) builder.addItem(res,value*amount*outputMultiplier);
                else builder.addItem(res,value*amount/inputReduction);
            }
        }
        return new SingleFactoryProduction(builder.buildPackage());
    }

    private ResourcePackage getRecipeResourcePackage(FactoryType factoryType) {
        Random rng = new Random();
        ResourcePackageBuilder recipeBuilder = new ResourcePackageBuilder();

        switch (factoryType) {
            case NOTHINGFORWOOD:
                recipeBuilder.addItem(ResourceType.WOOD, 3D);
                break;
            case WOODFORIRON:
                recipeBuilder.addItem(ResourceType.WOOD, -3D);
                recipeBuilder.addItem(ResourceType.IRON, 1D);
                break;
            case IRONFORGOLD:
                recipeBuilder.addItem(ResourceType.IRON, -2D);
                recipeBuilder.addItem(ResourceType.GOLD, 1D);
                break;
            case WOODFORGOLD:
                recipeBuilder.addItem(ResourceType.WOOD, -10D);
                recipeBuilder.addItem(ResourceType.GOLD, 1D);
                break;
            default:
                throw new RuntimeException("There is no ResourcePackage for this factory");
        }
        return recipeBuilder.buildPackage();
    }

    public Map<ResourceType,String> getFormatedInputs(){
        return resourcePackage.getFormatedInputs(true);
    }

    public Map<ResourceType,String> getFormatedOutputs(){
        return resourcePackage.getFormatedOutputs(true);
    }

    @Override
    public Collection<ResourceType> getResources() {
        return resourcePackage.getResources();
    }

    @Override
    public Double getValue(ResourceType resource) {
        return resourcePackage.getValue(resource);
    }
}
