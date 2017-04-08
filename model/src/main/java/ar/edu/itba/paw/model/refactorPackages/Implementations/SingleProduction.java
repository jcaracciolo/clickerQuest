package ar.edu.itba.paw.model.refactorPackages.Implementations;

import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.refactorPackages.*;

import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class SingleProduction extends ResourcePackage {

    public static Validator<Double> VALIDATOR = (d) -> d!=0;
    public static Creator<SingleProduction> CREATOR = (pb) -> new SingleProduction(pb.getResources());

    SingleProduction(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,false) + "/s";
    }

    public Map<ResourceType,String> getFormattedInputs(){
        return super.getFormattedInputs();
    }

    public Map<ResourceType,String> getFormattedOutputs(){
        return super.getFormattedOutputs();
    }
}
