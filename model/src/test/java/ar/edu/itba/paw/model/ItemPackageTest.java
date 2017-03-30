package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.impl.ItemPackageImpl;
import ar.edu.itba.paw.model.impl.ResourceImpl;
import ar.edu.itba.paw.model.interfaces.ItemPackage;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by jubenitez on 29/03/17.
 */
public class ItemPackageTest extends TestCase {

    @Test
    public void testItemPackageSum(){
        ItemPackage itemPackage = new ItemPackageImpl();
        ItemPackage itemPackage2 = new ItemPackageImpl();
        itemPackage.addItem(ItemFactory.getWood(),4L);
        itemPackage2.addItem(ItemFactory.getWood(),4L);
        assertEquals(itemPackage,itemPackage2);

        itemPackage2.addItemPackage(itemPackage);

        itemPackage = new ItemPackageImpl();
        itemPackage.addItem(ItemFactory.getWood(),8L);


        assertEquals(itemPackage,itemPackage2);
    }
}
