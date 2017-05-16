package ar.edu.itba.paw.model.packages;

import ar.edu.itba.paw.model.ResourceType;
//import sun.jvm.hotspot.oops.Mark;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by juanfra on 08/04/17.
 */
public abstract class ResourcePackage {
    protected Map<ResourceType,Double> resources;
    protected Formatter formatter;

    public Map<ResourceType,String> formatted(){
        Map<ResourceType,String> output = new TreeMap<>();
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
    public boolean contains(ResourceType resource){
        return resources.containsKey(resource);
    }

    protected Map<ResourceType,Double> generate(Map<ResourceType,Double> m, Validator<Double> v){
        Map<ResourceType,Double> newMap = new TreeMap<>();
        for(ResourceType r: m.keySet()) {
            double value = m.get(r);
            if(v.validates(value)) {
                newMap.put(r,m.get(r));
            } else {
                throw new RuntimeException("Validator invalid");
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

        if(value<=1000) {
            DecimalFormat df = new DecimalFormat(integers?"#":"#.##");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(value);
        }
        return coolFormat(Math.ceil(value));
    }

    protected Map<ResourceType,String> getFormattedInputs(){
        Map<ResourceType,String> map = new TreeMap<>();

        getInputs().forEach(
                (r,d) -> map.put(r,formatter.format(d))
        );

        return map;
    }

    protected Map<ResourceType,Double> getInputs(){
        Map<ResourceType,Double> map = new TreeMap<>();

        resources.entrySet().stream()
                .filter(m -> m.getValue() < 0)
                .forEach(m -> map.put(m.getKey(),-m.getValue()));

        return map;
    }

    protected Map<ResourceType,String> getFormattedOutputs(){
        Map<ResourceType,String> map = new TreeMap<>();

        getOutputs().forEach(
                (r,d) -> map.put(r,formatter.format(d))
        );

        return map;
    }

    protected Map<ResourceType,Double> getOutputs(){
        Map<ResourceType,Double> map = new TreeMap<>();

        resources.entrySet().stream()
                .filter(m -> m.getValue() > 0)
                .forEach(m -> map.put(m.getKey(),m.getValue()));

        return map;
    }

    public Map<ResourceType,Double> rawMap(){
        return generate(resources,(r)-> true);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourcePackage that = (ResourcePackage) o;

        if(that.resources == null) {
            return this.resources == null;
        }

        for(ResourceType r: resources.keySet()) {
            double d = resources.get(r);
            double thatD = that.resources.get(r);

            if(Math.signum(d) != Math.signum(thatD)) {
                return false;
            }

            if(d==0) {
                if(that.resources.get(r)!=0) {
                    return false;
                }
            } else if( Math.abs( thatD/d - 1 ) < 0.04 ) {
                return false;

            }
        }

        return true;

    }

    @Override
    public int hashCode() {
        return resources != null ? resources.hashCode() : 0;
    }
}
