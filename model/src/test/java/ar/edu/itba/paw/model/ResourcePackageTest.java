package ar.edu.itba.paw.model;

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
        ResourcePackage resourcePackage = FactoryType.IRONFORGOLD.getRecipe();
        System.out.println("outputs");
        for(ResourceType res: resourcePackage.getFormatedOutputs().keySet()){
            System.out.println(res + " " + resourcePackage.getFormatedOutputs().get(res));
        }
        System.out.println("inputs");
        for(ResourceType res: resourcePackage.getFormatedInputs().keySet()){
            System.out.println(res + " " + resourcePackage.getFormatedInputs().get(res));
        }

        ResourcePackage multi = resourcePackage.applyMultipliers(3d,1.5d,5d);

        System.out.println("outputs");
        for(ResourceType res: multi.getFormatedOutputs().keySet()){
            System.out.println(res + " " + multi.getFormatedOutputs().get(res));
        }
        System.out.println("inputs");
        for(ResourceType res: multi.getFormatedInputs().keySet()){
            System.out.println(res + " " + multi.getFormatedInputs().get(res));
        }

        Collection<ResourcePackage> collection = new ArrayList<>();
        collection.add(resourcePackage);collection.add(multi);
        ResourcePackage sum = ResourcePackageBuilder.sumPackages(collection);

        System.out.println("outputs");
        for(ResourceType res: sum.getFormatedOutputs().keySet()){
            System.out.println(res + " " + sum.getFormatedOutputs().get(res));
        }
        System.out.println("inputs");
        for(ResourceType res: sum.getFormatedInputs().keySet()){
            System.out.println(res + " " + sum.getFormatedInputs().get(res));
        }
    }


}
