package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public class Production {
    private Resources resource;
    private double rate;


    public String toString(){
        return resource + " " + rate + "/s";
    }

    public Production(Resources resource, double rate) {
        this.resource = resource;
        this.rate = rate;
    }

    public Resources getResource() {
        return resource;
    }

    public double getRate() {
        return rate;
    }
}
