package ar.edu.itba.paw.model.packages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.*;

import java.util.Map;

/**
 * Created by juanfra on 10/04/17.
 */
public class BaseCost extends ResourcePackage {
    public static Validator<Double> VALIDATOR = (d) -> d>=0 && d==Math.floor(d);
    public static Creator<BaseCost> CREATOR = (pb) -> new BaseCost(pb.getResources());

    BaseCost(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,true);
    }

    public FactoryCost calculateCost(double amount, double costReduction) {
        PackageBuilder<FactoryCost> builder = PackageType.FactoryCostType.packageBuilder();
        Transformer<Double,Double> toFactoryCost = (d) -> d*(amount+1)*costReduction;
        return super.map(builder,toFactoryCost);
    }
}
