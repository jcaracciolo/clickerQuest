package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.UpgradeType;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ar.edu.itba.paw.model.MyAssert.assertThrows;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class WealthTest {

    private static final Storage basicStorage;
    private static final Productions basicProductions;

    private long userId = 1;
    private final Double delta = 0.0001D;
    private PackageBuilder<Productions> productionsBuilder;
    private PackageBuilder<Storage> storageBuilder;
    private Factory factory = new Factory(userId,FactoryType.BOILER_BASE,1,1,1,1,0);
    private Upgrade nextUpgrade = factory.getNextUpgrade();

    static {
        Calendar now = Calendar.getInstance();
        PackageBuilder<Storage> storageB = Storage.packageBuilder();
        PackageBuilder<Productions> productionsB = Productions.packageBuilder();
        for(ResourceType res: ResourceType.values()) {
            productionsB.putItem(res,0D);
            storageB.putItemWithDate(res,0D,now);
        }

        basicStorage = storageB.buildPackage();
        basicProductions = productionsB.buildPackage();
    }

    @Before
    public void setUp() {
        productionsBuilder = Productions.packageBuilder().putItems(basicProductions);
        storageBuilder = Storage.packageBuilder().putItems(basicStorage).setDates(basicStorage);
    }

    @Test
    public void testCreate() {

        Storage storage = storageBuilder.buildPackage();
        Productions productions = productionsBuilder.buildPackage();
        Wealth wealth = new Wealth(userId,storage,productions);

        assertEquals(userId,wealth.getUserid());

        Storage updatedS = wealth.getStorage();
        assertTrue(updatedS.rawMap().size()>0);
        assertEquals(updatedS.getResources(),storage.getResources());
        updatedS.rawMap().forEach(
                (r,d) -> assertEquals(d,0,delta)
        );

        Productions updatedP = wealth.getProductions();
        assertTrue(updatedP.rawMap().size()>0);
        assertEquals(updatedP.getResources(),productions.getResources());
        updatedP.rawMap().forEach(
                (r,d) -> assertEquals(d,0D,delta)
        );

    }

    @Test
    public void testMoreProductionThanStorage() {
        PackageBuilder<Storage> sb = Storage.packageBuilder();
        PackageBuilder<Productions> pb = Productions.packageBuilder();
        sb.putItemWithDate(ResourceType.PEOPLE,1D,Calendar.getInstance());
        pb.putItem(ResourceType.PEOPLE,1D);
        pb.putItem(ResourceType.MONEY,1D);

        assertThrows(IllegalArgumentException.class,
                () -> new Wealth(1,sb.buildPackage(),pb.buildPackage())
                );

    }

    @Test
    public void testMoreStorageThanProductions() {
        PackageBuilder<Storage> sb = Storage.packageBuilder();
        PackageBuilder<Productions> pb = Productions.packageBuilder();
        sb.putItemWithDate(ResourceType.PEOPLE,1D,Calendar.getInstance());
        sb.putItemWithDate(ResourceType.MONEY,1D,Calendar.getInstance());
        pb.putItem(ResourceType.MONEY,1D);

        assertThrows(IllegalArgumentException.class,
                () -> new Wealth(1,sb.buildPackage(),pb.buildPackage())
        );

    }

    @Test
    public void testDifferentProductionsAndStorage() {
        PackageBuilder<Storage> sb = Storage.packageBuilder();
        PackageBuilder<Productions> pb = Productions.packageBuilder();
        sb.putItemWithDate(ResourceType.PEOPLE,1D,Calendar.getInstance());
        pb.putItem(ResourceType.MONEY,1D);

        assertThrows(IllegalArgumentException.class,
                () -> new Wealth(1,sb.buildPackage(),pb.buildPackage())
        );

    }

    @Test
    public void testStorageUpdate() {
        Calendar now = Calendar.getInstance();
        PackageBuilder<Storage> resetStorage = Storage.packageBuilder();
        Stream.of(ResourceType.values()).forEach(
                (r) -> {
                    productionsBuilder.addItem(r,1D);
                    resetStorage.putItemWithDate(r,0D,now);
                });

        Storage storage = storageBuilder.buildPackage();
        Productions productions = productionsBuilder.buildPackage();

        Calendar oneSecLater = Calendar.getInstance();
        oneSecLater.setTimeInMillis(now.getTimeInMillis() + 1000);

        Storage updated = storage.getUpdatedStorage(productions,oneSecLater);

        assertEquals(updated.getResources(),storage.getResources());
        assertTrue(updated.rawMap().size()>0);

        updated.rawMap().forEach(
                (r,d) -> assertEquals(d,1D,delta)
        );
    }

    @Test
    public void testFactoryPurchaseFail(){
        Productions productions = productionsBuilder.buildPackage();
        Storage storage = storageBuilder.buildPackage();
        Wealth basicWealth = new Wealth(userId,storage,productions);

        assertNull(basicWealth.purchaseResult(factory,1));
    }

    @Test
    public void testFactoryPurchaseSuccess() {
        factory.getCost().rawMap().forEach(
                (r,d) -> storageBuilder.addItem(r,d)
        );

        factory.getRecipe().getInputs().forEach(
                (r,d) -> productionsBuilder.addItem(r,d)
        );

        Wealth newWealth = new Wealth(userId,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
        Wealth afterPurchase = newWealth.purchaseResult(factory,1);
        assertNotNull(afterPurchase);

        assertTrue(afterPurchase.getStorage().rawMap().size()>0);
        afterPurchase.getStorage().rawMap().forEach(
                (r,d) -> assertEquals(d,0D,delta)
        );
    }

    @Test
    public void testUpgradeCost(){
        assertTrue(nextUpgrade.getCost() > 0);
    }

    @Test
    public void testUpgradePurchaseFail(){
        Productions p = productionsBuilder.buildPackage();
        Storage s = storageBuilder.buildPackage();
        Wealth basicWealth = new Wealth(userId,s,p);

        assertFalse(nextUpgrade.isBuyable(basicWealth));
        assertNull(basicWealth.upgradeResult(factory));
    }

    @Test
    public void testUpgradePurchaseSuccess() {
        storageBuilder.addItem(ResourceType.MONEY,nextUpgrade.getCost());
        Wealth newWealth = new Wealth(userId,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
        Wealth afterPurchase = newWealth.upgradeResult(factory);
        assertNotNull(afterPurchase);

        assertTrue(afterPurchase.getStorage().rawMap().size()>0);
        afterPurchase.getStorage().rawMap().forEach(
                (r,d) -> assertEquals(d,0,0)
        );
    }

    @Test
    public void testUpgradePurchaseCost() {
        factory.getFactoriesProduction().getOutputs().forEach(productionsBuilder::addItem);
        storageBuilder.addItem(ResourceType.MONEY,nextUpgrade.getCost());
        Wealth newWealth = new Wealth(userId,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
        Wealth afterPurchase = newWealth.upgradeResult(factory);

        assertTrue(afterPurchase.getStorage().rawMap().size()>0);
        afterPurchase.getStorage().rawMap().forEach(
                (r,d) -> assertEquals(d,0,0)
        );
    }

    @Test
    public void testUpgradeResultInputsReduced() {
        Double amountOfFactories = 5D;
        Factory factory = new Factory(userId,FactoryType.BOILER_BASE,amountOfFactories,1,1,1,0);
        factory.getFactoriesProduction().getInputs().forEach(productionsBuilder::addItem);

        productionsBuilder.addItems(factory.getFactoriesProduction());
        storageBuilder.addItem(ResourceType.MONEY,nextUpgrade.getCost());

        Wealth newWealth = new Wealth(userId,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
        Wealth afterPurchase = newWealth.upgradeResult(factory);

        assertEquals(nextUpgrade.getType(), UpgradeType.INPUT_REDUCTION);
        assertNotNull(afterPurchase);

        Map<ResourceType,Double> baseRecipeInputs = factory.getType().getBaseRecipe().getInputs();
        Map<ResourceType,Double> inputsReduced =
                afterPurchase.getProductions().rawMap().entrySet()
                        .stream()
                        .filter(
                                    m -> baseRecipeInputs.containsKey(m.getKey())
                        ).collect(
                                    Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)
                        );

        assertFalse(inputsReduced.isEmpty());
        inputsReduced.forEach(
                (r,d) -> {
                            assertTrue(d>0);
                            assertEquals(
                                    d,
                                    // Since the input cannot be negative, as you started with 0, now you
                                    // consume less so its 1-inputReduction
                                    baseRecipeInputs.get(r) * amountOfFactories *
                                            (1 - nextUpgrade.getInputReduction()),
                                    delta);
                        }
                );

    }

    @Test
    public void testUpgradeResultOutputMultiplied() {
        Double amountOfFactories = 3D;
        Factory factory = new Factory(userId,FactoryType.BOILER_BASE,amountOfFactories,1,1,1,1);
        Upgrade nextUpgrade = factory.getNextUpgrade();

        factory.getFactoriesProduction().getInputs().forEach(productionsBuilder::addItem);
        productionsBuilder.addItems(factory.getFactoriesProduction());
        storageBuilder.addItem(ResourceType.MONEY,nextUpgrade.getCost());

        Wealth newWealth = new Wealth(userId,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
        Wealth afterPurchase = newWealth.upgradeResult(factory);

        assertEquals(nextUpgrade.getType(), UpgradeType.OUTPUT_INCREASE);
        assertNotNull(afterPurchase);

        Map<ResourceType,Double> baseRecipeOutputs = factory.getType().getBaseRecipe().getOutputs();
        Map<ResourceType,Double> outputsMultiplied =
                afterPurchase.getProductions().rawMap().entrySet()
                        .stream()
                        .filter(
                                m -> baseRecipeOutputs.containsKey(m.getKey())
                        ).collect(
                        Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)
                );

        assertFalse(outputsMultiplied.isEmpty());
        outputsMultiplied.forEach(
                (r,d) -> {
                    assertTrue(d>0);
                    assertEquals(
                            d,
                            baseRecipeOutputs.get(r) * amountOfFactories  * nextUpgrade.getOutputMultiplier(),
                            delta);
                }
        );

    }

    @Test
    public void testUpgradeResultOutputsKeepIntact() {
        factory.getFactoriesProduction().getInputs().forEach(productionsBuilder::addItem);
        productionsBuilder.addItems(factory.getFactoriesProduction());
        storageBuilder.addItem(ResourceType.MONEY, nextUpgrade.getCost());

        Productions oldProductions = productionsBuilder.buildPackage();
        Wealth newWealth = new Wealth(userId, storageBuilder.buildPackage(), oldProductions);
        Wealth afterPurchase = newWealth.upgradeResult(factory);
        assertEquals(nextUpgrade.getType(), UpgradeType.INPUT_REDUCTION);

        Map<ResourceType,Double> baseRecipeInputs = factory.getType().getBaseRecipe().getInputs();
        Map<ResourceType, Double> inputsIntact =
                afterPurchase.getProductions().rawMap().entrySet()
                        .stream().filter(m -> !baseRecipeInputs.containsKey(m.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));


        assertFalse(inputsIntact.isEmpty());
        inputsIntact.forEach((r, d) -> assertEquals(
                oldProductions.getValue(r), d, delta)
        );
    }



}