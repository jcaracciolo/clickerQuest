package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.packages.Creator;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.packages.Validator;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.itba.paw.model.MyAssert.assertThrows;
import static org.junit.Assert.assertEquals;

/**
 * Created by juanfra on 13/04/17.
 */
public class PackageBuilderTest {

    private Validator<Double> VALIDATOR = (d) -> d>=5 && d<15;
    private Creator<TestPackage> CREATOR = TestPackage::new;

    private static ResourceType people = ResourceType.PEOPLE;
    private static ResourceType cardboard = ResourceType.CARDBOARD;
    private PackageBuilder<TestPackage> pb;


    private class TestPackage extends ResourcePackage {
        TestPackage(PackageBuilder<TestPackage> pb) {
            resources = generate(pb.getResources(),VALIDATOR);
        }
    }

    @Before
    public void setUp() {
        pb = new PackageBuilder<>(VALIDATOR,CREATOR);
    }

    @Test
    public void testValidatorPut() {
        assertThrows(RuntimeException.class,
                () -> pb.putItem(people,2D)
        );

        assertThrows(RuntimeException.class,
                () -> pb.putItem(people,-3D)
        );

        assertEquals(pb.getResources().size(),0);
        assertEquals(pb.getResources().size(),0);
    }

    @Test
    public void testValidatorAdd(){
        pb.putItem(people,5D);
        assertThrows(RuntimeException.class,
                () -> pb.addItem(people,10D)
        );
        pb.addItem(people,3D);
    }

    @Test
    public void testCreator() {

        pb.putItem(people,5D);
        assertThrows(RuntimeException.class,
                () -> pb.addItem(people,10D)
        );
        pb.addItem(people,3D);

        TestPackage testPackage = pb.buildPackage();

        assertEquals(testPackage.getResources().size(),1);
        testPackage.rawMap().forEach(
                (r,d) -> {
                    assertEquals(r,people);
                    assertEquals(d,8D,0);
                }
        );
    }

    @Test
    public void testRepeatedResources(){

        pb.putItem(people,5D);
        pb.putItem(cardboard,6D);

        assertThrows(RuntimeException.class,
                () -> pb.putItem(cardboard,6D)
                );
        assertThrows(RuntimeException.class,
                () -> pb.putItem(people,5D)
        );

        TestPackage testPackage = pb.buildPackage();
        assertEquals(testPackage.getResources().size(),2);
    }

    @Test
    public void testMultipleResources(){

        Double delta = 0D;

        pb.putItem(people,5D);
        pb.putItem(cardboard,6D);
        pb.addItem(people,2.5);
        pb.addItem(cardboard,3.6);

        TestPackage testPackage = pb.buildPackage();
        assertEquals(testPackage.getResources().size(),2);
        assertEquals(testPackage.getValue(people),7.5,delta);
        assertEquals(testPackage.getValue(cardboard),9.6,delta);
    }

}
