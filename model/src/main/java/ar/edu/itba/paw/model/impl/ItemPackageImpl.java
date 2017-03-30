package ar.edu.itba.paw.model.impl;

import ar.edu.itba.paw.model.interfaces.Item;
import ar.edu.itba.paw.model.interfaces.Resource;
import ar.edu.itba.paw.model.interfaces.ItemPackage;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by jubenitez on 29/03/17.
 */
public class ItemPackageImpl implements ItemPackage {
    private HashMap<Item, Long> items;

    public ItemPackageImpl() {
        this.items = new HashMap<Item, Long>();
    }

    public boolean addItem(Item item, Long amount){
        if(items.containsKey(item)) items.put(item, items.get(item) + amount);
        else items.put(item,amount);
        return items.get(item) >= 0;
    }

    public boolean addItemPackage(ItemPackage itemPackage){
        boolean allPositive = true;
        for (Item item : itemPackage.getItems()){
            if(! addItem(item, itemPackage.getItemDelta(item))) allPositive = false;
        }
        return allPositive;
    }

    public long getItemDelta(Item resource) {
        return items.get(resource);
    }

    public Collection<Item> getItems(){
        return items.keySet();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        boolean first = true;
        for (Item item : items.keySet()){
            if(!first) stringBuffer.append(",");
            else first = false;
            stringBuffer.append(item + "->" + items.get(item) );
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPackageImpl that = (ItemPackageImpl) o;

        return items != null ? items.equals(that.items) : that.items == null;
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }
}
