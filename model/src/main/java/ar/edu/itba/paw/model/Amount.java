package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public class Amount {
    public Resources resource;
    public double amount;

    public Amount(Resources resource, double amount) {
        this.resource = resource;
        this.amount = amount;
    }

    public Resources getResource() {
        return resource;
    }

    public double getAmount() {
        return amount;
    }

    public String toString(){
        return amount + resource.toString();
    }
}
