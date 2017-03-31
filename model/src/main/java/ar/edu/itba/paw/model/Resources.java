package ar.edu.itba.paw.model;
import com.sun.istack.internal.Nullable;
import sun.plugin.dom.exception.InvalidStateException;

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
            default: throw new InvalidStateException("There is no string for this resource");
        }
    }

    @Nullable
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
