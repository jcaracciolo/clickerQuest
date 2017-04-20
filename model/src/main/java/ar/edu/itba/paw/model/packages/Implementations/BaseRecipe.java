package ar.edu.itba.paw.model.packages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.Creator;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.packages.Validator;

import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class BaseRecipe extends ResourcePackage {
    public static Validator<Double> VALIDATOR = (d) -> d!=0;
    public static Creator<BaseRecipe> CREATOR = (pb) -> new BaseRecipe(pb.getResources());

    private BaseRecipe(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,false) + "/s";
    }

    public static PackageBuilder<BaseRecipe> packageBuilder() {
        return new PackageBuilder<>(VALIDATOR,CREATOR);
    }

    public Recipe calculateRecipe(double inputReduction, double outputMultiplier, long level){
        PackageBuilder<Recipe> builder = Recipe.packageBuilder();
        resources.forEach(
                (r,d) -> builder.putItem(r,d>0 ? d*outputMultiplier : d*inputReduction)
        );
        return builder.buildPackage();
    }

    public FactoriesProduction calculateProduction(double amount, double inputReduction, double outputMultiplier, long level){
        PackageBuilder<FactoriesProduction> builder = FactoriesProduction.packageBuilder();
        resources.forEach(
                (r,d) -> builder.putItem(r,d>0 ? d*outputMultiplier*amount : d*inputReduction*amount)
        );
        return builder.buildPackage();
    }

    public Map<ResourceType,String> getFormattedInputs(){
        return super.getFormattedInputs();
    }

    public Map<ResourceType,String> getFormattedOutputs(){
        return super.getFormattedOutputs();
    }

    public Map<ResourceType,Double> getInputs() {
        return super.getInputs();
    }

    public Map<ResourceType,Double> getOutputs(){
        return super.getOutputs();
    }

}
