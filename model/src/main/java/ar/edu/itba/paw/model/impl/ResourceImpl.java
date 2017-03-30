package ar.edu.itba.paw.model.impl;

import ar.edu.itba.paw.model.interfaces.Resource;

/**
 * Created by jubenitez on 29/03/17.
 */
public class ResourceImpl implements Resource {
    private String name;

    public ResourceImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceImpl resource = (ResourceImpl) o;

        return name != null ? name.equals(resource.name) : resource.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
