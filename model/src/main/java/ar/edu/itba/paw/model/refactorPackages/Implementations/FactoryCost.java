package ar.edu.itba.paw.model.refactorPackages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.refactorPackages.Creator;
import ar.edu.itba.paw.model.refactorPackages.Formatter;
import ar.edu.itba.paw.model.refactorPackages.ResourcePackage;
import ar.edu.itba.paw.model.refactorPackages.Validator;

import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class FactoryCost extends ResourcePackage {

    public static Validator<Double> VALIDATOR = (d) -> d>=0 && d==Math.floor(d);
    public static Creator<FactoryCost> CREATOR = (pb) -> new FactoryCost(pb.getResources());

    FactoryCost(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = ResourcePackage::coolFormat;
    }

}
