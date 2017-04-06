package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.ResourceType;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jubenitez on 05/04/17.
 */
public class FactoryCost implements Package{
    private ResourcePackage resourcePackage;

    public FactoryCost(FactoryType factoryType) {
        this.resourcePackage = getCost(factoryType);
    }

    private ResourcePackage getCost(FactoryType factoryType) {
        ResourcePackageBuilder costBuilder = new ResourcePackageBuilder();

        switch (factoryType) {
            case NOTHINGFORWOOD:
                costBuilder.addItem(ResourceType.WOOD,10D);
                break;
            case WOODFORIRON:
                costBuilder.addItem(ResourceType.WOOD,100D);
                break;
            case IRONFORGOLD:
                costBuilder.addItem(ResourceType.WOOD,100D);
                costBuilder.addItem(ResourceType.IRON,100D);
            case WOODFORGOLD:
                costBuilder.addItem(ResourceType.IRON,100D);
                costBuilder.addItem(ResourceType.WOOD,100D);
                costBuilder.addItem(ResourceType.GOLD,1000D);
                break;
            default:
                throw new RuntimeException("There is no ResourcePackage for this factory");
        }
        return costBuilder.buildPackage();
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
