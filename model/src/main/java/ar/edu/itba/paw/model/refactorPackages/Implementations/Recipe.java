package ar.edu.itba.paw.model.refactorPackages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.refactorPackages.*;

import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class Recipe extends ResourcePackage {
    public static Validator<Double> VALIDATOR = (d) -> d!=0;
    public static Creator<Recipe> CREATOR = (pb) -> new Recipe(pb.getResources());

    Recipe(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,false) + "/s";
    }

    public SingleProduction applyMultipliers(double amount, double inputReduction, double outputMultiplier, int level) {
        PackageBuilder<SingleProduction> builder = PackageType.SingleProductionType.packageBuilder();
        if(amount > 0) {
            for (ResourceType res : resources.keySet()){
                Double value = resources.get(res);
                if(value > 0) {
                    builder.putItem(res,value*amount*outputMultiplier);
                }
                else builder.putItem(res,value*amount/inputReduction);
            }
        }

        return builder.buildPackage();
    }

    public Map<ResourceType,String> getFormattedInputs(){
        return super.getFormattedInputs();
    }

    public Map<ResourceType,String> getFormattedOutputs(){
        return super.getFormattedOutputs();
    }

}
