package ar.edu.itba.paw.model.packages.Implementations;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.packages.Creator;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.packages.Validator;

import java.util.Map;

/**
 * Created by juanfra on 08/04/17.
 */
public class Productions extends ResourcePackage {
    public static Validator<Double> VALIDATOR = (d) -> d>=0;
    public static Creator<Productions> CREATOR = (pb) -> new Productions(pb.getResources());

    Productions(Map<ResourceType, Double> map) {
        resources = super.generate(map,VALIDATOR);
        formatter = (d) -> formatValue(d,false) + "/s";
    }
}