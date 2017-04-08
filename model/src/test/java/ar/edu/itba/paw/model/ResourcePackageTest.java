package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.refactorPackages.Implementations.Productions;
import ar.edu.itba.paw.model.refactorPackages.Implementations.Recipe;
import ar.edu.itba.paw.model.refactorPackages.Implementations.SingleProduction;
import ar.edu.itba.paw.model.refactorPackages.PackageBuilder;
import ar.edu.itba.paw.model.refactorPackages.PackageType;
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
        Recipe recipe = FactoryType.WOODFORIRON.getRecipe();
//        ResourcePackage.printPackage(recipe);

        Collection<SingleProduction> productions = new ArrayList<>();
        SingleProduction singleProduction = recipe
                .applyMultipliers(2,1,2D,1);
        singleProduction.printPackage();

        productions.add(singleProduction);

        singleProduction = FactoryType.IRONFORGOLD.getRecipe()
                .applyMultipliers(4,1.2,2D,1);
        singleProduction.printPackage();

        productions.add(singleProduction);

        PackageBuilder<Productions> builder = PackageType.ProductionType.packageBuilder();
        for(SingleProduction sp: productions){
            builder.putItems(sp);
        }
        Productions finalProductions = builder.buildPackage();
        finalProductions.printPackage();
    }
}
