package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public enum ResourceType {
    WOOD, IRON, GOLD, MONEY;

    public String toString() {
        switch (this) {
            case WOOD: return "wood";
            case IRON: return "iron";
            case GOLD: return "gold";
            case MONEY: return "money";
            default: throw new RuntimeException("There is no string for this resource");
        }
    }

    public static ResourceType fromString(String s){
        switch (s.toLowerCase()) {
            case "wood": return WOOD;
            case "iron": return IRON;
            case "gold": return GOLD;
            case "money": return MONEY;
            default: return null;
        }
    }

    public int getId() {
        switch (this) {
            case WOOD: return 0;
            case IRON: return 1;
            case GOLD: return 2;
            case MONEY: return 3;
            default: throw new RuntimeException("There is no id for this resource");
        }
    }

    public static ResourceType fromId(int id){
        switch (id) {
            case 0: return WOOD;
            case 1: return IRON;
            case 2: return GOLD;
            case 3: return MONEY;
            default: return null;
        }
    }
}
