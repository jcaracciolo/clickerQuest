package ar.edu.itba.paw.model;

import java.util.Arrays;
import java.util.Optional;


public enum ResourceType {
    CIRCUITS(12,"circuit-type",5),
    CARDBOARD(11,"cardboard-type",0.1),
    COPPER_CABLE(10,"copper-cable-type",0.5),
    COPPER(9,"copper-type",1),
    METAL_SCRAP(8,"metal-scrap-type",1.1),
    RUBBER(7,"rubber-type",0.4),
    TIRES(6,"tires-type",0.8),
    IRON(5,"iron-type",0.5),
    PEOPLE(4,"people-type",0.9),
    MONEY(3,"money-type",1),
    GOLD(2,"gold-type",2),
    PLASTIC(1,"plastic-type",5),
    POWER(0,"power-type",10);

    private int id;
    private String nameCode;
    private double price;
    private double popularity;

    ResourceType(int id,String name,double price) {
        this.id = id;
        this.nameCode = name;
        this.price = price;
        this.popularity = 1;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPrice() {
        return price * popularity;
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
        return Arrays.stream(ResourceType.values()).filter((r) -> r.getId() ==id).findAny().orElse(null);
    }
    public static Optional<ResourceType> fromName(String nameCode){
        return Arrays.stream(ResourceType.values()).filter((r) -> r.nameCode.equals(nameCode)).findAny();
    }
}
