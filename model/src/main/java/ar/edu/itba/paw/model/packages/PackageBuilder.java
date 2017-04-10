package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.ResourceType;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class PackageBuilder<T extends ResourcePackage> {

    private Map<ResourceType, Double> resources;
    private Map<ResourceType, Calendar> lastUpdated;
    private Validator<Double> validator;
    private Creator<T> creator;

    public PackageBuilder(Validator<Double> validator, Creator<T> creator) {
        resources = new HashMap<>();
        lastUpdated = new HashMap<>();
        this.validator = validator;
        this.creator = creator;
    }

    public PackageBuilder<T> putItem(ResourceType resource, Double amount){
        if(!validator.validates(amount)) {
            return null;
        }else if(resources.containsKey(resource)) {
            return null;
        } else {
            resources.put(resource,amount);
            return this;
        }
    }

    public PackageBuilder<T> addItem(ResourceType resource,Double amount) {
        if(!validator.validates(amount)) {
            return null;
        }else if(resources.containsKey(resource)) {
            resources.put(resource,resources.get(resource) + amount);
            return this;
        } else {
            resources.put(resource,amount);
            return this;
        }
    }

    public PackageBuilder<T> putItems(ResourcePackage resources) {
        for(ResourceType res: resources.resources.keySet()){
            putItem(res,resources.getValue(res));
        }
        return this;
    }

    public PackageBuilder<T> addItems(ResourcePackage resources) {
        for(ResourceType res: resources.resources.keySet()){
            addItem(res,resources.getValue(res));
        }
        return this;
    }

    /**
     * Accepts lastUpdated time for a given, already put resource
     * @param resource
     * @param time
     * @return
     */
    public PackageBuilder<T> setLastUpdated(ResourceType resource, Calendar time){
        if(!lastUpdated.containsKey(resource) && resources.containsKey(resource)) {
            lastUpdated.put(resource,time);
            return this;
        } else {
            return null;
        }
    }

    public PackageBuilder<T> putItemWithDate(ResourceType resource,Double amount,Calendar time){
        return this.putItem(resource,amount).setLastUpdated(resource,time);
    }

    public Map<ResourceType, Double> getResources() {
        return resources;
    }

    public T buildPackage(){
        return creator.create(this);
    }

    public Map<ResourceType,Calendar> lastUpdated() {
        return lastUpdated;
    }
}
