package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.Exception.ValidatorException;
import ar.edu.itba.paw.model.packages.Creator;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.packages.Validator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.management.openmbean.KeyAlreadyExistsException;

import static org.junit.Assert.assertEquals;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = ValidatorException.class)
    public void testValidatorPutLess() {
        pb.putItem(people, 2D);
    }

    @Test
    public void testValidatorAddMore() {
        pb.putItem(people, 5D);
        thrown.expect(ValidatorException.class);
        pb.addItem(people, 10D);
    }

    @Test
    public void testValidatorAddLess() {
        pb.putItem(people, 5D);
        thrown.expect(ValidatorException.class);
        pb.addItem(people, -10D);
    }

    @Test
    public void testCreator() {
        pb.putItem(people,5D);
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
        thrown.expect(KeyAlreadyExistsException.class);
        pb.putItem(cardboard,6D);
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
