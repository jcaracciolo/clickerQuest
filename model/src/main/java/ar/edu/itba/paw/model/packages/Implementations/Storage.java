package ar.edu.itba.paw.model.packages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.*;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class Storage extends ResourcePackage {

    public static Validator<Double> VALIDATOR = (d) -> d>=0 && d==Math.floor(d);
    public static Creator<Storage> CREATOR = (pb) -> new Storage(pb.getResources(),pb.lastUpdated());

    private Map<ResourceType,Calendar> lastUpdated;

    Storage(Map<ResourceType, Double> map,Map<ResourceType,Calendar> lastUpdated) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,true);

        this.lastUpdated = new HashMap<>();
        for(ResourceType r: lastUpdated.keySet()) {
            this.lastUpdated.put(r,lastUpdated.get(r));
        }
    }

    public Map<ResourceType,Calendar> getLastUpdated() {
        return lastUpdated;
    }

    public Calendar getLastUpdated(ResourceType resourceType) {
        return lastUpdated.get(resourceType);
    }
    public Storage getUpdatedStorage(Productions production) {
        return getUpdatedStorage(production,Calendar.getInstance());
    }

    public Storage getUpdatedStorage(Productions production, Calendar now){
        PackageBuilder<Storage> resourcePackageBuilder = PackageType.StorageType.packageBuilder();

        for (ResourceType resourceType: resources.keySet()){
            resourcePackageBuilder.putItem(resourceType,resources.get(resourceType));
        }

        for (ResourceType resourceType: production.getResources()){
            long seconds = ChronoUnit.SECONDS.between(lastUpdated.get(resourceType).toInstant(), now.toInstant());

            Double value = Math.ceil(production.getValue(resourceType) * seconds);
            if(value <0 ) throw new RuntimeException("Negative Production in storage calculation!");

            resourcePackageBuilder.addItem(resourceType, value);
            resourcePackageBuilder.setLastUpdated(resourceType, now);
        }
        return resourcePackageBuilder.buildPackage();
    }
}
