package ar.edu.itba.paw.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Julian Benitez on 5/17/2017.
 */
public class UpgradeTest {
    //TODO this is wrong
    @Test
    public void upgradeTestNoInputs() throws Exception{
        Upgrade upgrade;
        for (int i = 1; i < 35; i++) {
            upgrade = Upgrade.getUpgrade(FactoryType.STOCK_INVESTMENT_BASE,i);
            System.out.println("level " + i + " " +upgrade.getType());
        }
    }
    @Test
    public void upgradeTestFull() throws Exception{
        Upgrade upgrade;
        for (int i = 1; i < 35; i++) {
            upgrade = Upgrade.getUpgrade(FactoryType.CIRCUIT_MAKER_BASE,i);
            System.out.println("level " + i + " " +upgrade.getType());
        }
    }

}
