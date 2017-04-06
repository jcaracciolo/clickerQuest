package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.ResourceType;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jubenitez on 05/04/17.
 */
public class Production implements Package {
    private ResourcePackage resourcePackage;

    public Production(Collection<SingleFactoryProduction> productions) {
        this.resourcePackage = sumPackages(productions);
    }

    public Production(ResourcePackage resourcePackage){
        this.resourcePackage = resourcePackage;
    }

    private ResourcePackage sumPackages(Collection<SingleFactoryProduction> productions ) {
        ResourcePackageBuilder builder = new ResourcePackageBuilder();

        for (SingleFactoryProduction production:productions){
            for (ResourceType resource : production.getResources()) {
                builder.addItem(resource, production.getValue(resource));
            }
        }

        return builder.buildPackage();
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
