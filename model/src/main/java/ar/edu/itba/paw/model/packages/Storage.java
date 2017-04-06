package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.ResourceType;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;

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
//
//    public Storage(Storage storage, Production production, Map<ResourceType,Calendar> lastUpdated) {
//        this.resourcePackage = calculateProducedResources(storage,production,lastUpdated);
//    }

    private ResourcePackage calculateProducedResources(Storage storage, Production production, Map<ResourceType, Calendar> lastUpdated){
        ResourcePackageBuilder resourcePackageBuilder = new ResourcePackageBuilder();
        Calendar rightNow = Calendar.getInstance();

        for (ResourceType resourceType: storage.getResources()){
            resourcePackageBuilder.addItem(resourceType,storage.getValue(resourceType));
        }
        for (ResourceType resourceType: production.getResources()){
            long seconds = ChronoUnit.SECONDS.between(lastUpdated.get(resourceType).toInstant(), rightNow.toInstant());
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
}
