package ar.edu.itba.paw.model;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by juanfra on 31/03/17.
 */
public enum ResourceType {
    POWER(0,"power"), PLASTIC(1,"plastic"), GOLD(2,"gold"),
    MONEY(3,"money"), PEOPLE(4,"people"), IRON(5,"iron"),
    TIRES(6,"tires"), RUBBER(7,"rubber"),METAL_SCRAP(8,"metal scrap"),
    COPPER(9,"copper"), COPPER_CABLE(10,"cable"), CARDBOARD(11,"cardboard");
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
        return Arrays.asList(ResourceType.values()).stream().filter((r) -> r.getId() ==id).findAny().get();
    }
}
