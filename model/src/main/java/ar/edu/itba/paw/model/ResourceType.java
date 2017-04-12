package ar.edu.itba.paw.model;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by juanfra on 31/03/17.
 */
public enum ResourceType {
    WOOD(0), IRON(1), GOLD(2), MONEY(3);
    private int id;

    private static HashMap<ResourceType,String> names = new HashMap<>();
    static {
        names.put(WOOD,"wood");
        names.put(IRON,"iron");
        names.put(GOLD,"gold");
        names.put(MONEY,"money");
    }

    ResourceType(int id) {
        this.id = id;
    }

    public String toString() {
        return names.get(fromId(id));
    }

    public int getId() {
        return id;
    }

    public static ResourceType fromId(int id){
        return Arrays.asList(ResourceType.values()).stream().filter((r) -> r.getId() ==id).findAny().get();
    }
}
