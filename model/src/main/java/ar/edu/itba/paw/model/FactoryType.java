package ar.edu.itba.paw.model;

import java.util.*;

/**
 * Created by juanfra on 31/03/17.
 */
public enum FactoryType {
    NOTHINGFORWOOD,
    WOODFORIRON,
    IRONFORGOLD,
    WOODFORGOLD;

    public int getId() {
        switch (this){
            case NOTHINGFORWOOD: return 0;
            case WOODFORIRON: return 1;
            case IRONFORGOLD: return 2;
            case WOODFORGOLD: return 3;
            default: throw new RuntimeException("There is no ID for the corresponding FactoryType");
        }
    }

    public static FactoryType fromId(int id){
        for (FactoryType f: FactoryType.values()){
            if(f.getId() == id){
                return f;
            }
        }

        return null;
    }
}
