package ar.edu.itba.paw.model.packages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.*;

import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class FactoryCost extends ResourcePackage {

    public static Validator<Double> VALIDATOR = (d) -> d>=0 && d==Math.floor(d);
    public static Creator<FactoryCost> CREATOR = (pb) -> new FactoryCost(pb.getResources());

    private FactoryCost(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,true);
    }

    public static PackageBuilder<FactoryCost> packageBuilder() {
        return new PackageBuilder<>(VALIDATOR,CREATOR);
    }
}
