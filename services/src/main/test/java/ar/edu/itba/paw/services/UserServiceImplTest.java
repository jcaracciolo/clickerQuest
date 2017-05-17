package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    private String username = "Daniel";
    private String password = "l0b0--";
    private String img = "1.img";

    @Test
    public void test(){
        assertTrue(true);
    }

    @Test
    public void testCreateUser(){

        User user = userService.create(username,password,img);

        assertNotNull(user);
        assertEquals(user.getUsername(),username);
        assertEquals(user.getPassword(),password);
        assertEquals(user.getProfileImage(),img);

        User u1 = userService.findById(user.getId());
        assertEquals(user,u1);
        User u2 = userService.findByUsername(username);
        assertEquals(user,u2);
    }

    @Test
    public void testGetUserWealth() {

        PackageBuilder<Storage> storageB = Storage.packageBuilder();
        PackageBuilder<Productions> productionsB = Productions.packageBuilder();

        Arrays.stream(ResourceType.values()).forEach((r) -> storageB.putItemWithDate(r, 0D, Calendar.getInstance()));
        Arrays.stream(ResourceType.values()).forEach((r) -> productionsB.putItem(r, 0D));

//        TODO fix this in UserDao
        storageB.addItem(ResourceType.MONEY,ResourceType.initialMoney());
        FactoryType.PEOPLE_RECRUITING_BASE.getBaseRecipe().getOutputs().forEach(productionsB::addItem);
        FactoryType.STOCK_INVESTMENT_BASE.getBaseRecipe().getOutputs().forEach(productionsB::addItem);


        User user = userService.create(username,password,img);

        Wealth w = new Wealth(user.getId(),storageB.buildPackage(),productionsB.buildPackage());
        Wealth otherW = userService.getUserWealth(user.getId());
        assertEquals(w, otherW);
    }





}
