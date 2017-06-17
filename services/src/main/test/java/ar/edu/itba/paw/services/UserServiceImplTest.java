package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.Paginating;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    // For reset purposes
    @Autowired
    ar.edu.itba.paw.services.MockUserDao mockUserDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MarketDao marketDao;

    private String username = "Daniel";
    private String password = "l0b0--";
    private String img = "1.img";
    private int id = 0;

//    private Wealth w = new Wealth(0,null,null);

    private double initialMoneyProduction = FactoryType.STOCK_INVESTMENT_BASE.getBaseRecipe().getValue(ResourceType.MONEY);
    private double initialPeopleProduction = FactoryType.PEOPLE_RECRUITING_BASE.getBaseRecipe().getValue(ResourceType.PEOPLE);
    private double initialMoney = ResourceType.initialMoney()
            - FactoryType.STOCK_INVESTMENT_BASE.getBaseCost().getValue(ResourceType.MONEY)
            - FactoryType.PEOPLE_RECRUITING_BASE.getBaseCost().getValue(ResourceType.MONEY);

    @Before
    public void setup(){
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);
        Mockito.when(marketDao.registerPurchase(Matchers.any(StockMarketEntry.class))).thenReturn(true);

        //Setup userdao mock
//        Mockito.when(userDao.create(username,password,img)).thenReturn(new User(id,username,password,img,id,null));
//        Mockito.when(userDao.getUserWealth(0)).thenReturn(w);
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
    public void testGetUserById(){
        User user = userService.create(username,password,img);
        User u1 = userService.findById(user.getId());
        assertEquals(user,u1);
    }

    @Test
    public void testGetUserByUsername(){
        User user = userService.create(username,password,img);
        User u1 = userService.findByUsername(user.getUsername());
        assertEquals(user,u1);
    }

    public void testFindByKeywordAll(){
        User user = userService.create(username,password,img);
        User user2 = userService.create(username + "2",password,img);
        Collection<User> users = userService.findByKeyword(username);
        assertEquals(users.size(),2);
        assertTrue(users.contains(user));
        assertTrue(users.contains(user2));

    }

    @Test
    public void testFindByKeywordSome(){
        User user = userService.create(username,password,img);
        User user2 = userService.create("test",password,img);
        Collection<User> users = userService.findByKeyword("est");
        assertEquals(users.size(),1);
        assertFalse(users.contains(user));
        assertTrue(users.contains(user2));

    }

    @Test
    public void testGlobalRanking(){
        User user = userService.create(username,password,img);
        User user2 = userService.create(username + "2",password,img);
        User user3 = userService.create(username + "3",password,img);

        assertTrue(userService.purchaseFactory(user2.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1));

        assertTrue(userService.purchaseFactory(user3.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1));
        assertTrue(userService.purchaseFactory(user3.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1));

        int rank1 = userService.getGlobalRanking(user.getId());
        int rank2 = userService.getGlobalRanking(user2.getId());
        int rank3 = userService.getGlobalRanking(user3.getId());
        assertEquals(rank3,1);
        assertEquals(rank2,2);
        assertEquals(rank1,3);

    }


    @Test
    public void testGetUserWealth() {
        User user = userService.create(username,password,img);
        Wealth wealth = userService.getUserWealth(user.getId());

        assertNotNull(wealth);
        Map<ResourceType,Double> storageMap = wealth.getStorage().rawMap();
        Map<ResourceType,Double> productionsMap = wealth.getProductions().rawMap();
        assertFalse(storageMap.isEmpty());
        assertFalse(productionsMap.isEmpty());


        storageMap.forEach(
                (r,d)-> {
                    if(r==ResourceType.MONEY) {
                        assertEquals(d,initialMoney,0D);
                    } else {
                        assertEquals(d,0,0D);
                    }
                }
        );


       productionsMap.forEach(
                (r,d) -> {
                    switch (r){
                        case MONEY:
                            assertEquals(d,initialMoneyProduction,0D); break;
                        case PEOPLE:
                            assertEquals(d,initialPeopleProduction,0D); break;
                        default:
                            assertEquals(d,0D,0D);
                    }
                }
        );
    }

    @Test
    public void testGetUserFactories(){
        User user = userService.create(username,password,img);
        Collection<Factory> factories = userService.getUserFactories(user.getId());
        assertEquals(factories.size(),FactoryType.values().length);
        assertTrue(factories.contains(FactoryType.STOCK_INVESTMENT_BASE.defaultFactory(user.getId())));
        assertTrue(factories.contains(FactoryType.PEOPLE_RECRUITING_BASE.defaultFactory(user.getId())));
        factories.forEach((f)->{
            switch (f.getType()){
                case PEOPLE_RECRUITING_BASE:
                case STOCK_INVESTMENT_BASE:
                    assertEquals(1D,f.getAmount(),0D);
                    break;
                default:
                    assertEquals(0D,f.getAmount(),0D);
            }
        });

    }

    @Test
    public void testPurchaseFactorySuccess(){
        User user = userService.create(username,password,img);
        assertTrue(userService.purchaseFactory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1));
        Collection<Factory> factories = userService.getUserFactories(user.getId());
        assertEquals(factories.size(),FactoryType.values().length);
        factories.forEach((f)->{
            switch (f.getType()){
                case STOCK_INVESTMENT_BASE:
                    assertEquals(1D,f.getAmount(),0D);
                    break;
                case PEOPLE_RECRUITING_BASE:
                    assertEquals(2D,f.getAmount(),0D);
                    break;
                default:
                    assertEquals(0D,f.getAmount(),0D);
                    break;
            }
        });
    }

    @Test
    public void testPurchaseFactoryFail(){
        User user = userService.create(username,password,img);
        assertFalse(userService.purchaseFactory(user.getId(),FactoryType.CABLE_MAKER_BASE,1));
        Collection<Factory> factories = userService.getUserFactories(user.getId());
        assertEquals(factories.size(),FactoryType.values().length);
        factories.forEach((f)->{
            switch (f.getType()){
                case STOCK_INVESTMENT_BASE:
                case PEOPLE_RECRUITING_BASE:
                    assertEquals(1D,f.getAmount(),0D);
                    break;
                default:
                    assertEquals(0D,f.getAmount(),0D);
            }
        });
    }

    @Test
    public void purchaseFactoryUpdateWealthTest(){
        User user = userService.create(username,password,img);
        assertTrue(userService.purchaseFactory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1));
        Factory factory = new Factory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1D,1,1,1,0);
        Wealth wealth = userService.getUserWealth(user.getId());
        assertEquals(wealth.getStorage().getValue(ResourceType.MONEY),
                initialMoney - factory.getCost().getValue(ResourceType.MONEY),
                0D);
    }

    @Test
    public void purchaseMultipleFactoriesUpdateWealthTest(){
        User user = userService.create(username,password,img);
        assertTrue(userService.purchaseFactory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1));
        assertTrue(userService.purchaseFactory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1));
        assertTrue(userService.purchaseFactory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1));
        double factory1Cost = new Factory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,1D,1,1,1,0)
                .getCost().getValue(ResourceType.MONEY);
        double factory2Cost = new Factory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,2D,1,1,1,0)
                .getCost().getValue(ResourceType.MONEY);
        double factory3Cost = new Factory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE,3D,1,1,1,0)
                .getCost().getValue(ResourceType.MONEY);;
        Wealth wealth = userService.getUserWealth(user.getId());
        assertEquals(wealth.getStorage().getValue(ResourceType.MONEY),
                initialMoney - (factory1Cost + factory2Cost + factory3Cost),
                0D);
    }

    @Test
    public void testPurchaseUpgradeSuccess(){
        User user = userService.create(username,password,img);
        assertTrue(userService.purchaseUpgrade(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE));
        Collection<Factory> factories = userService.getUserFactories(user.getId());
        factories.forEach((f)->{
            switch (f.getType()){
                case PEOPLE_RECRUITING_BASE:
                    assertEquals(1,f.getLevel());
                    break;
                default:
                    assertEquals(0,f.getLevel());
            }
        });
    }

    @Test
    public void testPurchaseUpgradeFail(){
        User user = userService.create(username,password,img);
        assertFalse(userService.purchaseUpgrade(user.getId(),FactoryType.CABLE_MAKER_BASE));
        Collection<Factory> factories = userService.getUserFactories(user.getId());
        factories.forEach((f)-> assertEquals(0,f.getLevel()));
    }

    @Test
    public void testPurchaseResource() {
        User user = userService.create(username,password,img);
        userService.purchaseResourceType(user.getId(),ResourceType.CARDBOARD,1D);
        Wealth w = userService.getUserWealth(user.getId());
        w.getStorage().rawMap().forEach( (r,d)-> {
                    switch (r){
                        case CARDBOARD:
                            assertEquals(d,1D,0D);
                            break;
                        case MONEY:
                            assertEquals(d,initialMoney - ResourceType.CARDBOARD.getBasePrice(),0D);
                            break;
                        default:
                            assertEquals(d,0D,0D);
                    }
                }
        );
    }

    @Test
    public void testSellResource() {
        User user = userService.create(username,password,img);
        double cardboard = 3;
        userService.purchaseResourceType(user.getId(),ResourceType.CARDBOARD,cardboard);
        double money = initialMoney - cardboard*ResourceType.CARDBOARD.getBasePrice();

        userService.sellResourceType(user.getId(),ResourceType.CARDBOARD,1D);
        Wealth w = userService.getUserWealth(user.getId());
        w.getStorage().rawMap().forEach( (r,d)-> {
                    switch (r){
                        case CARDBOARD:
                            assertEquals(d,cardboard - 1,0D);
                            break;
                        case MONEY:
                            assertEquals(d,money + ResourceType.CARDBOARD.getBasePrice(),0D);
                            break;
                        default:
                            assertEquals(d,0D,0D);
                    }
                }
        );
    }

    @Test
    public void testPaginatorFirstPage() {
        int totalUsers = 11;
        int perPage = 5;
        int firstPage = 1;
        int pages = (int)Math.ceil(totalUsers/(double)perPage);
        for(int i = 0;i<totalUsers;i++){
            userService.create(username + i,password,img);
        }

        Paginating<User> users = userService.globalUsers(firstPage,perPage);
        assertEquals(users.getPage(),firstPage);
        assertEquals(users.getItemsPerPage(),perPage);
        assertEquals(users.getTotalItems(),totalUsers);
        assertEquals(users.getTotalPages(),pages);

        assertEquals(users.getItems().size(),perPage);
        Set<User> uniqueUsers = new HashSet<>();
        users.getItems().forEach((u)->assertTrue(uniqueUsers.add(u)));
    }

}
