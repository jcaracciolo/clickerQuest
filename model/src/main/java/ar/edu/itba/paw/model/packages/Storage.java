package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.ResourceType;

import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by jubenitez on 05/04/17.
 */
public class Storage implements Package{
    private ResourcePackage resourcePackage;
    Map<ResourceType, Calendar> lastUpdated;

    public Storage(ResourcePackage resourcePackage, Map<ResourceType,Calendar> lastUpdated) {
        this.lastUpdated = lastUpdated;
        this.resourcePackage = resourcePackage;
    }

    public Storage getUpdatedStorage(Production production, Calendar now){
        ResourcePackage ress = calculateProducedResources(this,production,now);
        return new Storage(ress,new HashMap<>());
    }

    private ResourcePackage calculateProducedResources(Storage storage, Production production, Calendar now){
        ResourcePackageBuilder resourcePackageBuilder = new ResourcePackageBuilder();

        for (ResourceType resourceType: storage.getResources()){
            resourcePackageBuilder.addItem(resourceType,storage.getValue(resourceType));
        }
        for (ResourceType resourceType: production.getResources()){
            long seconds = ChronoUnit.SECONDS.between(lastUpdated.get(resourceType).toInstant(), now.toInstant());
            Double value = production.getValue(resourceType) * seconds;
            if(value <0 ) throw new RuntimeException("Negative Production in storage calculation!");
            resourcePackageBuilder.addItem(resourceType, value );
        }
        return resourcePackageBuilder.buildPackage();
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

    public Storage purchase(Factory f) {
        FactoryCost fc = f.getCost();
        ResourcePackageBuilder<Storage> newStorage = new ResourcePackageBuilder<>();
        for(ResourceType r: resourcePackage.getResources()) {
            if(!fc.getResources().contains(r)) {
                newStorage.addItem(r,resourcePackage.getValue(r));
            } else if (resourcePackage.getValue(r) < fc.getValue(r)) {
                return null;
            } else {
                newStorage.addItem(r,resourcePackage.getValue(r) - fc.getValue(r));
            }
        }

        return new Storage(newStorage.buildPackage(),lastUpdated);
    }
}
