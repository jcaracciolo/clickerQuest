package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.ResourceType;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jubenitez on 05/04/17.
 */
public class SingleFactoryProduction implements Package{
    private ResourcePackage resourcePackage;

    public SingleFactoryProduction(ResourcePackage resourcePackage) {
        this.resourcePackage = resourcePackage;
    }

    public Map<ResourceType,String> getFormatedInputs(){
        return resourcePackage.getFormatedInputs(false);
    }

    public Map<ResourceType,String> getFormatedOutputs(){
        return resourcePackage.getFormatedOutputs(false);
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
