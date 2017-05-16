package ar.edu.itba.paw.model;

import java.util.Arrays;
import java.util.Optional;


public enum ResourceType {
    CIRCUITS(12,"circuit-type"),
    CARDBOARD(11,"cardboard-type"),
    COPPER_CABLE(10,"copper-cable-type"),
    COPPER(9,"copper-type"),
    METAL_SCRAP(8,"metal-scrap-type"),
    RUBBER(7,"rubber-type"),
    TIRES(6,"tires-type"),
    IRON(5,"iron-type"),
    PEOPLE(4,"people-type"),
    MONEY(3,"money-type"),
    GOLD(2,"gold-type"),
    PLASTIC(1,"plastic-type"),
    POWER(0,"power-type");

    private int id;
    private String nameCode;
    private double price;

    ResourceType(int id,String name) {
        this.id = id;
        this.nameCode = name;
        price = (id + 1) * 2;
    }

    public double getPrice() {
        return price;
    }

    public String getNameCode() {
        return nameCode;
    }

    public int getId() {
        return id;
    }

    public static double initialMoney() {
        return 13000;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static ResourceType fromId(int id){
        return Arrays.stream(ResourceType.values()).filter((r) -> r.getId() ==id).findAny().get();
    }
    public static Optional<ResourceType> fromName(String nameCode){
        return Arrays.stream(ResourceType.values()).filter((r) -> r.nameCode.equals(nameCode)).findAny();
    }
}
