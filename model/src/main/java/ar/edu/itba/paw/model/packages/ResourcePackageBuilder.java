package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.ResourceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by julian on 4/4/17.
 */
public class ResourcePackageBuilder<T> {
    private Map<ResourceType, Double> resources;

    public ResourcePackageBuilder() {
        resources = new HashMap<>();
    }

    public boolean addItem(ResourceType resource, Double amount){
        if(resources.containsKey(resource)) {
            resources.put(resource, resources.get(resource) + amount);
            return true;
        } else {
            resources.put(resource,amount);
            return false;
        }
    }

    public ResourcePackage buildPackage(){
        return new ResourcePackage(resources);
    }

//    public static ResourcePackage sumPackages(Collection<Production> resourcePackages) {
//        ResourcePackageBuilder builder = new ResourcePackageBuilder();
//
//        for (ResourcePackage resourcePackage:resourcePackages){
//            for (ResourceType resource : resourcePackage.getResources()) {
//                builder.addItem(resource, resourcePackage.getValue(resource));
//            }
//        }
//
//        return builder.buildPackage();
//    }
}
