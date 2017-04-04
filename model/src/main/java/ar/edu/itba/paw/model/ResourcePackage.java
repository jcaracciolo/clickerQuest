package ar.edu.itba.paw.model;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by julian on 4/4/17.
 */
public class ResourcePackage {

    private HashMap<Resources, Double> resources;

    public ResourcePackage(HashMap<Resources, Double> items) {
        this.resources = items;
    }


    //This method asumes that "this" is a "recipe" meaning it does not have any multipliers added, only the raw sum of the factory recipe
    //Positive resource values are interpreted as outputs, negative ones as inputs. OF A SINGLE FACTORY TYPE.
    //THIS IS NOT MEANT TO BE USED WHEN THE PACKAGE REPRESENTS THE OUTPUT OF MORE THAN 1 FACTORY TYPE.
    public ResourcePackage applyMultipliers(double amount, double inputReduction, double outputMultiplier)
    {
        ResourcePackageBuilder builder = new ResourcePackageBuilder();
        if(amount <= 0) return builder.buildPackage();

        for (Resources res : getResources()){
            Double value = resources.get(res);
            if(value > 0) builder.addItem(res,value*amount*outputMultiplier);
            else builder.addItem(res,value*amount/inputReduction);
        }
        return builder.buildPackage();
    }

    Set<Resources> getResources(){
        return resources.keySet();
    }

    public Double getValue(Resources resource){
        return resources.get(resource);
    }

    public  String formatResource(Resources resource){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(resource.toString());
        stringBuilder.append(" = ");
        stringBuilder.append(coolFormat(resources.get(resource),0));
        return stringBuilder.toString();
    }

    public Map<Resources,String> getFormatedInputs(){
        Map<Resources,String> map = new HashMap<>();

        for (Resources res: getResources()){
            Double value = resources.get(res);
            if(value < 0) map.put(res,formatValue(-value));
        }
        return map;
    }

    public Map<Resources,String> getFormatedOutputs(){
        Map<Resources,String> map = new HashMap<>();

        for (Resources res: getResources()){
            Double value = resources.get(res);
            if(value > 0) map.put(res,formatValue(value));
        }
        return map;
    }

    static String formatValue(Double value){
        if(value<1000) {
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(value);
        }
        return coolFormat(value,0);
    }

    private static char[] c = new char[]{'k', 'm', 'b', 't'};
    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
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
