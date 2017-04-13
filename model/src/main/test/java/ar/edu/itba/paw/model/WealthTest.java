package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for Wealth model
 */
public class WealthTest {

    private PackageBuilder<Productions> productionsBuilder;
    private PackageBuilder<Storage> storageBuilder;
    private long userId = 1;

    @Before
    public void setUp() {
        productionsBuilder = Productions.packageBuilder();
        storageBuilder = Storage.packageBuilder();
    }

    @Test
    public void testCreate() {
        Calendar now = Calendar.getInstance();
        for(ResourceType res: ResourceType.values()) {
            productionsBuilder.putItem(res,0D);
            storageBuilder.putItemWithDate(res,0D,now);
        }

        Productions p = productionsBuilder.buildPackage();
        Storage s = storageBuilder.buildPackage();
        Wealth wealth = new Wealth(userId,s,p);

        assertEquals(userId,wealth.getUserid());

        Double delta = 0D;

        Storage updatedS = wealth.getStorage();
        assertEquals(updatedS.getResources(),s.getResources());
        updatedS.rawMap().forEach(
                (r,d) -> assertEquals(d,0,delta)
        );

        Productions updatedP = wealth.getProductions();
        assertEquals(updatedP.getResources(),p.getResources());
        updatedP.rawMap().forEach(
                (r,d) -> assertEquals(d,0D,delta)
        );

    }

    @Test
    public void testStorageUpdate() {
        Calendar now = Calendar.getInstance();
        for(ResourceType res: ResourceType.values()) {
            storageBuilder.putItemWithDate(res,0D,now);
            productionsBuilder.putItem(res,1D);
        }

        Storage storage = storageBuilder.buildPackage();
        Productions productions = productionsBuilder.buildPackage();

        Calendar oneSecLater = Calendar.getInstance();
        oneSecLater.setTimeInMillis(now.getTimeInMillis() + 1000);

        Storage updated = storage.getUpdatedStorage(productions,oneSecLater);

        assertEquals(updated.getResources(),storage.getResources());
        Double delta = 0D;

        updated.rawMap().forEach(
                (r,d) -> assertEquals(d,1D,delta)
        );
    }

    @Test
    public void testPurchase() {
        Calendar now = Calendar.getInstance();
        for(ResourceType res: ResourceType.values()) {
            productionsBuilder.putItem(res,0D);
            storageBuilder.putItemWithDate(res,0D,now);
        }

        Upgrade upgrade = Upgrade.getUpgrade(FactoryType.BOILER_BASE,1);
        Factory factory = new Factory(userId,FactoryType.BOILER_BASE,1,1,1,1,upgrade);

        Productions p = productionsBuilder.buildPackage();
        Storage s = storageBuilder.buildPackage();
        Wealth basicWealth = new Wealth(userId,s,p);

        assertNull(basicWealth.purchaseResult(factory));

        factory.getCost().rawMap().forEach(
                (r,d) -> storageBuilder.addItem(r,d)
        );

        factory.getRecipe().getInputs().forEach(
                (r,d) -> productionsBuilder.addItem(r,d)
        );

        Wealth newWealth = new Wealth(userId,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
        Wealth afterPurchase = newWealth.purchaseResult(factory);
        assertNotNull(afterPurchase);

        Double delta = 0D;
        afterPurchase.getStorage().rawMap().forEach(
                (r,d) -> assertEquals(d,0D,delta)
        );
    }

}