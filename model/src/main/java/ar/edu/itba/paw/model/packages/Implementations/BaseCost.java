package ar.edu.itba.paw.model.packages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.Creator;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.packages.Validator;

import java.util.Map;

/**
 * Created by juanfra on 10/04/17.
 */
public class BaseCost extends ResourcePackage {
    private final static Validator<Double> VALIDATOR = (d) -> d>=0 && d==Math.floor(d);
    private final static Creator<BaseCost> CREATOR = (pb) -> new BaseCost(pb.getResources());

    private BaseCost(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,true);
    }

    public static PackageBuilder<BaseCost> packageBuilder() {
        return new PackageBuilder<>(VALIDATOR,CREATOR);
    }

    public FactoryCost calculateCost(double currentAmount, double costReduction, double amountToBuy) {
        PackageBuilder<FactoryCost> builder = FactoryCost.packageBuilder();
        resources.forEach(
                (r,d) -> builder.putItem(r, Math.floor(d* gDiff(currentAmount,currentAmount+amountToBuy)*costReduction) )
        );
        return builder.buildPackage();
    }



    private double gDiff(double start, double end)
    {
        return ( end* ( end+ 1 ) ) / 2 - ( start* ( start+ 1 ) ) / 2 ;
    }
}
