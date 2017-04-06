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


    //This method asumes that "this" is a "recipe" meaning it does not have any multipliers added, only the raw sum of the factory recipe
    //Positive resource values are interpreted as outputs, negative ones as inputs. OF A SINGLE FACTORY TYPE.
    //THIS IS NOT MEANT TO BE USED WHEN THE PACKAGE REPRESENTS THE OUTPUT OF MORE THAN 1 FACTORY TYPE.
    public ResourcePackage applyMultipliers(double amount, double inputReduction, double outputMultiplier)
    {
        ResourcePackageBuilder builder = new ResourcePackageBuilder();
        if(amount <= 0) return builder.buildPackage();

        for (ResourceType res : getResources()){
            Double value = resources.get(res);
            if(value > 0) builder.addItem(res,value*amount*outputMultiplier);
            else builder.addItem(res,value*amount/inputReduction);
        }
        return builder.buildPackage();
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
            if(value > 0) map.put(res,formatValue(value, integers));
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
        return coolFormat(value,0);
    }

    private static char[] c = new char[]{'k', 'm', 'b', 't'};
    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invocation.
     * @param n the number to format
     * @param iteration in fact this is the class from the array c
     * @return a String representing the number n formatted in a cool looking way.
     */
    private static String coolFormat(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)

        return (d < 1000? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99)? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : coolFormat(d, iteration+1));

    }
}
