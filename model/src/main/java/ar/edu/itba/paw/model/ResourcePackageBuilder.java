package ar.edu.itba.paw.model;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by julian on 4/4/17.
 */
public class ResourcePackageBuilder {
    private HashMap<Resources, Double> resources;

    public ResourcePackageBuilder() {
        resources = new HashMap<>();
    }

    public boolean addItem(Resources resource, Double amount){
        if(resources.containsKey(resource)) resources.put(resource, resources.get(resource) + amount);
        else resources.put(resource,amount);
        return resources.get(resource) >= 0;
    }

    public ResourcePackage buildPackage(){
        return new ResourcePackage(resources);
    }

    public static ResourcePackage sumPackages(Collection<ResourcePackage> resourcePackages) {
        ResourcePackageBuilder builder = new ResourcePackageBuilder();

        for (ResourcePackage resourcePackage:resourcePackages){
            for (Resources resource : resourcePackage.getResources()) {
                builder.addItem(resource, resourcePackage.getValue(resource));
            }
        }

        return builder.buildPackage();
    }
}
