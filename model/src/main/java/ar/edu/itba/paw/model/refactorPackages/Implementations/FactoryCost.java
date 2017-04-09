package ar.edu.itba.paw.model.refactorPackages.Implementations;

import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.refactorPackages.*;

import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class FactoryCost extends ResourcePackage {

    public static Validator<Double> VALIDATOR = (d) -> d>=0 && d==Math.floor(d);
    public static Creator<FactoryCost> CREATOR = (pb) -> new FactoryCost(pb.getResources());

    FactoryCost(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,true);
    }

    public FactoryCost applyMultipliers(double amount, double costReduction) {
        PackageBuilder<FactoryCost> builder = PackageType.FactoryCostType.packageBuilder();
        if(amount > 0) {
            for (ResourceType res : resources.keySet()){
                Double value = resources.get(res);
                builder.putItem(res,value / costReduction * amount);
            }
        }

        return builder.buildPackage();
    }

}
