package ar.edu.itba.paw.model;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by juanfra on 31/03/17.
 */
public enum ResourceType {
    CARDBOARD(11,"cardboard"),
    COPPER_CABLE(10,"cable"),
    COPPER(9,"copper"),
    METAL_SCRAP(8,"metal scrap"),
    RUBBER(7,"rubber"),
    TIRES(6,"tires"),
    IRON(5,"iron"),
    PEOPLE(4,"people"),
    MONEY(3,"money"),
    GOLD(2,"gold"),
    PLASTIC(1,"plastic"),
    POWER(0,"power");

    private int id;
    private String name;

    ResourceType(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static ResourceType fromId(int id){
        return Arrays.stream(ResourceType.values()).filter((r) -> r.getId() ==id).findAny().get();
    }
}
