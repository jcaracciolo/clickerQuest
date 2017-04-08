package ar.edu.itba.paw.model.refactorPackages;

import ar.edu.itba.paw.model.ResourceType;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by juanfra on 08/04/17.
 */
public abstract class ResourcePackage {
    protected Map<ResourceType,Double> resources;
    protected Formatter formatter;

    public Map<ResourceType,String> formatted(){
        Map<ResourceType,String> output = new HashMap<>();
        for(ResourceType r: resources.keySet()){
            output.put(r,formatter.format(resources.get(r)));
        }
        return output;
    }
    public Set<ResourceType> getResources(){
        return resources.keySet();
    }
    public Double getValue(ResourceType resource){
        return resources.get(resource);
    }

    protected Map<ResourceType,Double> generate(Map<ResourceType,Double> m, Validator<Double> v){
        Map<ResourceType,Double> newMap = new HashMap<>();
        for(ResourceType r: m.keySet()) {
            double value = m.get(r);
            if(v.validates(value)) {
                newMap.put(r,m.get(r));
            } else {
                throw new RuntimeException("Storage values must be positive doubles without decimal part");
            }
        }

        return newMap;
    }

    private static char[] c = new char[]{'k', 'm', 'b', 't'};

    public void printPackage(){
        System.out.println("outputs");
        for(ResourceType res: getFormattedOutputs().keySet()){
            System.out.println(res + " " + getFormattedOutputs().get(res));
        }
        System.out.println("inputs");
        for(ResourceType res: getFormattedInputs().keySet()){
            System.out.println(res + " " + getFormattedInputs().get(res));
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

    protected Map<ResourceType,String> getFormattedInputs(){
        Map<ResourceType,String> map = new HashMap<>();

        for (ResourceType res: getResources()){
            Double value = resources.get(res);
            if(value < 0) map.put(res,formatter.format(-value));
        }
        return map;
    }

    protected Map<ResourceType,String> getFormattedOutputs(){
        Map<ResourceType,String> map = new HashMap<>();

        for (ResourceType res: getResources()){
            Double value = resources.get(res);
            map.put(res,formatter.format(value));
        }
        return map;
    }

    private static String coolFormat(double n){
        return coolFormat(n,0);
    }

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
