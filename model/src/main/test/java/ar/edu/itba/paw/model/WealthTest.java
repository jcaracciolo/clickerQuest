package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

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
        assertTrue(updatedS.rawMap().size()>0);
        assertEquals(updatedS.getResources(),s.getResources());
        updatedS.rawMap().forEach(
                (r,d) -> assertEquals(d,0,delta)
        );

        Productions updatedP = wealth.getProductions();
        assertTrue(updatedP.rawMap().size()>0);
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

        assertTrue(updated.rawMap().size()>0);
        updated.rawMap().forEach(
                (r,d) -> assertEquals(d,1D,delta)
        );
    }

    @Test
    public void testFactoryPurchase() {
        Calendar now = Calendar.getInstance();
        for(ResourceType res: ResourceType.values()) {
            productionsBuilder.putItem(res,0D);
            storageBuilder.putItemWithDate(res,0D,now);
        }

        Factory factory = new Factory(userId,FactoryType.BOILER_BASE,1,1,1,1,0);

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
        assertTrue(afterPurchase.getStorage().rawMap().size()>0);
        afterPurchase.getStorage().rawMap().forEach(
                (r,d) -> assertEquals(d,0D,delta)
        );
    }

    @Test
    public void testUpgradePurchase() {
        Calendar now = Calendar.getInstance();
        for(ResourceType res: ResourceType.values()) {
            productionsBuilder.putItem(res,0D);
            storageBuilder.putItemWithDate(res,0D,now);
        }

        Factory factory = new Factory(userId,FactoryType.BOILER_BASE,1,1,1,1,0);
        factory.getFactoriesProduction().getOutputs().forEach(productionsBuilder::addItem);

        Upgrade nextUpgrade = factory.getNextUpgrade();
        assertTrue(nextUpgrade.getCost() > 0);

        Productions p = productionsBuilder.buildPackage();
        Storage s = storageBuilder.buildPackage();
        Wealth basicWealth = new Wealth(userId,s,p);

        assertFalse(nextUpgrade.isBuyable(basicWealth));
        assertNull(basicWealth.upgradeResult(null));
        assertNull(basicWealth.upgradeResult(factory));

        storageBuilder.addItem(ResourceType.MONEY,nextUpgrade.getCost());

        Wealth newWealth = new Wealth(userId,storageBuilder.buildPackage(),p);
        Wealth afterPurchase = newWealth.upgradeResult(factory);
        assertNotNull(afterPurchase);

        assertTrue(afterPurchase.getStorage().rawMap().size()>0);
        afterPurchase.getStorage().rawMap().forEach(
                (r,d) -> assertEquals(d,0,0)
        );
    }

    @Test
    public void testUpgradeResult() {

        Calendar now = Calendar.getInstance();
        for(ResourceType res: ResourceType.values()) {
            productionsBuilder.putItem(res,0D);
            storageBuilder.putItemWithDate(res,0D,now);
        }

        Double amountOfFactories = 5D;
        Factory factory = new Factory(userId,FactoryType.PEOPLE_RECRUITING_BASE,amountOfFactories,1,1,1,0);
        factory.getFactoriesProduction().getOutputs().forEach(productionsBuilder::addItem);

        Upgrade nextUpgrade = factory.getNextUpgrade();
        storageBuilder.addItem(ResourceType.MONEY,nextUpgrade.getCost());

        Wealth newWealth = new Wealth(userId,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
        Wealth afterPurchase = newWealth.upgradeResult(factory);
        assertNotNull(afterPurchase);

        Map<ResourceType,Double> baseRecipeOutputs = factory.getType().getBaseRecipe().getOutputs();

        Map<ResourceType,Double> outputsMultiplied =
                afterPurchase.getProductions().rawMap().entrySet()
                            .stream().filter(
                                    m -> baseRecipeOutputs.containsKey(m.getKey())
                ).collect(Collectors.toMap(m->m.getKey(), m-> m.getValue()));

        assertFalse(outputsMultiplied.isEmpty());
        outputsMultiplied.forEach(
                (r,d) -> {
                            assertTrue(d>0);
                            assertEquals(
                                    d,
                                    baseRecipeOutputs.get(r) * amountOfFactories * nextUpgrade.getOutputMultiplier(),
                                    0D);
                        }
                );

        Map<ResourceType,Double> outputsIntact =
        afterPurchase.getProductions().rawMap().entrySet()
                .stream().filter(m-> !baseRecipeOutputs.containsKey(m.getKey()))
                .collect(Collectors.toMap(m->m.getKey(), m-> m.getValue()));

        assertFalse(outputsIntact.isEmpty());
        outputsIntact.forEach((r,d) -> assertEquals(0,d,0D));
    }



}