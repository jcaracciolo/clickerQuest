package ar.edu.itba.paw.model.packages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.Creator;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.packages.Validator;

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

    public static PackageBuilder<Storage> packageBuilder() {
        return new PackageBuilder<>(VALIDATOR,CREATOR);
    }

    private Storage(Map<ResourceType, Double> map,Map<ResourceType,Calendar> lastUpdated) {
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

    public Storage getUpdatedStorage(Productions productions) {
        return getUpdatedStorage(productions,Calendar.getInstance());
    }

    public Storage getUpdatedStorage(Productions productions, Calendar now){
        PackageBuilder<Storage> resourcePackageBuilder = Storage.packageBuilder();

        for (ResourceType resourceType: resources.keySet()){
            resourcePackageBuilder.putItem(resourceType,resources.get(resourceType));
        }

        for (ResourceType resourceType: productions.getResources()){
            long seconds = ChronoUnit.SECONDS.between(lastUpdated.get(resourceType).toInstant(), now.toInstant());

            Double value = Math.ceil(productions.getValue(resourceType) * seconds);
            if(value <0 ) throw new RuntimeException("Negative Production in storage calculation!");

            resourcePackageBuilder.addItem(resourceType, value);
            resourcePackageBuilder.setLastUpdated(resourceType, now);
        }
        return resourcePackageBuilder.buildPackage();
    }

}
