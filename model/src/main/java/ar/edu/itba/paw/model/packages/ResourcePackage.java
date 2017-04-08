package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.ResourceType;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by julian on 4/4/17.
 */
public class ResourcePackage {

    private Map<ResourceType, Double> resources;

    public ResourcePackage(Map<ResourceType, Double> items) {
        this.resources = items;
    }

    public Set<ResourceType> getResources(){
        return resources.keySet();
    }

    public Double getValue(ResourceType resource){
        return resources.get(resource);
    }


    public Map<ResourceType,String> getFormatedInputs(Boolean integers){
        Map<ResourceType,String> map = new HashMap<>();

        for (ResourceType res: getResources()){
            Double value = resources.get(res);
            if(value < 0) map.put(res,formatValue(-value,integers));
        }
        return map;
    }

    public Map<ResourceType,String> getFormatedInputs(){
        return getFormatedInputs(false);
    }

    public Map<ResourceType,String> getFormatedOutputs(){
        return getFormatedOutputs(false);
    }

    public Map<ResourceType,String> getFormatedOutputs(Boolean integers){
        Map<ResourceType,String> map = new HashMap<>();

        for (ResourceType res: getResources()){
            Double value = resources.get(res);
            map.put(res,formatValue(value, integers));
        }
        return map;
    }

    public static void printPackage(Package resourcePackage){
        System.out.println("outputs");
        for(ResourceType res: resourcePackage.getFormatedOutputs().keySet()){
            System.out.println(res + " " + resourcePackage.getFormatedOutputs().get(res));
        }
        System.out.println("inputs");
        for(ResourceType res: resourcePackage.getFormatedInputs().keySet()){
            System.out.println(res + " " + resourcePackage.getFormatedInputs().get(res));
        }
    }

    public static String formatValue(Double value, Boolean integers){
        if(value<1000) {
            DecimalFormat df = new DecimalFormat(integers?"#":"#.##");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(value);
        }
        return value.toString(); //coolFormat(value,0);
    }

    private static char[] c = new char[]{'k', 'm', 'b', 't'};

}
