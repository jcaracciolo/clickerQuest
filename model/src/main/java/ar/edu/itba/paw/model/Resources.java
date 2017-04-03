package ar.edu.itba.paw.model;

/**
 * Created by juanfra on 31/03/17.
 */
public enum Resources {
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

    public static Resources fromString(String s){
        switch (s.toLowerCase()) {
            case "wood": return WOOD;
            case "iron": return IRON;
            case "gold": return GOLD;
            case "money": return MONEY;
            default: return null;
        }
    }
}
