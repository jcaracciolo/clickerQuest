package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class ResourcePackageTest
    extends TestCase
{
    @org.junit.Test
    public void testCreate() {
        Recipe recipe = new Recipe(FactoryType.WOODFORIRON);
//        ResourcePackage.printPackage(recipe);

        Collection<SingleFactoryProduction> productions = new ArrayList<>();
        SingleFactoryProduction singleFactoryProduction = recipe
                .applyMultipliers(2,1,2D,1);
        ResourcePackage.printPackage(singleFactoryProduction);

        productions.add(singleFactoryProduction);

        singleFactoryProduction = new Recipe(FactoryType.IRONFORGOLD)
                .applyMultipliers(4,1.2,2D,1);
        ResourcePackage.printPackage(singleFactoryProduction);

        productions.add(singleFactoryProduction);

        Production production = new Production(productions);
        ResourcePackage.printPackage(production);
    }
}
