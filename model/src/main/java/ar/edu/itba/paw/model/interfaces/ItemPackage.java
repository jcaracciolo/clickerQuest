package ar.edu.itba.paw.model.interfaces;

import java.util.Collection;

/**
 * Created by jubenitez on 29/03/17.
 */
public interface ItemPackage {
    long getItemDelta(Item resource);

    Collection<Item> getItems();

    boolean addItem(Item item, Long amount);

    boolean addItemPackage(ItemPackage itemPackage);

}
