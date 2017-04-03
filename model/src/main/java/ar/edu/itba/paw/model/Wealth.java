package ar.edu.itba.paw.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by juanfra on 03/04/17.
 */
public class Wealth {

    public long owner;
    public Date lastUpdated;
    //Already calculated data, ready for display
    public Map<Resources,Double> storage;
    public Map<Resources,Double> productions;

    public Wealth(long owner, Date lastUpdated, Map<Resources,Double> storage, Map<Resources,Double> productions) {
        this.owner = owner;
        this.lastUpdated = lastUpdated;
        this.storage = storage;
        this.productions = productions;
    }

    //public ResourcePackage Map<Resource,Double>getInputs (solo inputs en valor positivo, formateados(?)
    //                          Map<Resource,Double>getOutputs (solo inputs en valor positivo, formateados(?)


}
