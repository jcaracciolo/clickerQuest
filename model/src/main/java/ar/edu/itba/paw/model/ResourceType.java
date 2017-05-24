package ar.edu.itba.paw.model;

import java.util.Arrays;
import java.util.Optional;


public enum ResourceType {
    CIRCUITS(12,"circuit-type",50),
    CARDBOARD(11,"cardboard-type",1),
    COPPER_CABLE(10,"copper-cable-type",5),
    COPPER(9,"copper-type",1),
    METAL_SCRAP(8,"metal-scrap-type",11),
    RUBBER(7,"rubber-type",4),
    TIRES(6,"tires-type",8),
    IRON(5,"iron-type",5),
    PEOPLE(4,"people-type",9),
    MONEY(3,"money-type",10),
    GOLD(2,"gold-type",20),
    PLASTIC(1,"plastic-type",40),
    POWER(0,"power-type",80);

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

    public int getPrice() {
        return (int)Math.ceil(price * popularity);
    }

    public int getBasePrice() {
        return (int)Math.ceil(price);
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
