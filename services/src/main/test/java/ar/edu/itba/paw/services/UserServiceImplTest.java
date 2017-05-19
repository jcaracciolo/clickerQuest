package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import org.junit.Before;
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

    // For reset purposes
    @Autowired
    MockUserDao mockUserDao;

    private String username = "Daniel";
    private String password = "l0b0--";
    private String img = "1.img";

    @Before
    public void setup(){
        mockUserDao.clear();
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

        storageB.addItem(ResourceType.MONEY,ResourceType.initialMoney());

        assertEquals(FactoryType.PEOPLE_RECRUITING_BASE.getBaseCost().rawMap().size(),1);
        Double recruitCost = FactoryType.PEOPLE_RECRUITING_BASE.getBaseCost().getValue(ResourceType.MONEY);
        assertNotNull(recruitCost);
        assertEquals(FactoryType.STOCK_INVESTMENT_BASE.getBaseCost().rawMap().size(),1);
        Double investmentCost = FactoryType.STOCK_INVESTMENT_BASE.getBaseCost().getValue(ResourceType.MONEY);
        assertNotNull(investmentCost);

        storageB.addItem(ResourceType.MONEY, -recruitCost);
        storageB.addItem(ResourceType.MONEY, -investmentCost);
        FactoryType.PEOPLE_RECRUITING_BASE.getBaseRecipe().getOutputs().forEach(productionsB::addItem);
        FactoryType.STOCK_INVESTMENT_BASE.getBaseRecipe().getOutputs().forEach(productionsB::addItem);


        User user = userService.create(username,password,img);

        Wealth w = new Wealth(user.getId(),storageB.buildPackage(),productionsB.buildPackage());
        Wealth otherW = userService.getUserWealth(user.getId());
        assertEquals(w, otherW);
    }



}
