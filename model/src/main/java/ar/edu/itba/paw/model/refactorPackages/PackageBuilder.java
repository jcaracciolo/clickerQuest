package ar.edu.itba.paw.model.refactorPackages;

import ar.edu.itba.paw.model.ResourceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class PackageBuilder<T extends ResourcePackage> {

    private Map<ResourceType, Double> resources;
    private Validator<Double> validator;
    private Creator<T> creator;

    public PackageBuilder(Validator<Double> validator, Creator<T> creator) {
        resources = new HashMap<>();
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

    public Map<ResourceType, Double> getResources() {
        return resources;
    }

    public T buildPackage(){
        return creator.create(this);
    }
}
