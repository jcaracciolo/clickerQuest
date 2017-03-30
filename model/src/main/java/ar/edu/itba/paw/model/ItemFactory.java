package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.impl.ResourceImpl;
import ar.edu.itba.paw.model.interfaces.Item;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by jubenitez on 29/03/17.
 */
public abstract class ItemFactory {
    public static Item getWood(){
        return new ResourceImpl("Wood");
    }
}
