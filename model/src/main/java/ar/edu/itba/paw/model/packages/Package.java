package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.ResourceType;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jubenitez on 05/04/17.
 */
public interface Package {
    Map<ResourceType,String> getFormatedInputs();

    Map<ResourceType,String> getFormatedOutputs();

    Collection<ResourceType> getResources();

    Double getValue(ResourceType resource);
}
