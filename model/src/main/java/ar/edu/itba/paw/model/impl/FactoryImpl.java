package ar.edu.itba.paw.model.impl;

import ar.edu.itba.paw.model.interfaces.Factory;
import ar.edu.itba.paw.model.interfaces.ItemPackage;

/**
 * Created by jubenitez on 29/03/17.
 */
public class FactoryImpl implements Factory {
    private String name;
    private ItemPackage itemPackage;

    public FactoryImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ItemPackage getDeltas() {
        return itemPackage;
    }
}
