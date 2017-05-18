package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.Upgrade;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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
            System.out.println("level " + i + " " +upgrade.getNextUpgradeType());
        }
    }
    @Test
    public void upgradeTestFull() throws Exception{
        Upgrade upgrade;
        for (int i = 1; i < 35; i++) {
            upgrade = Upgrade.getUpgrade(FactoryType.CIRCUIT_MAKER_BASE,i);
            System.out.println("level " + i + " " +upgrade.getNextUpgradeType());
        }
    }

}
