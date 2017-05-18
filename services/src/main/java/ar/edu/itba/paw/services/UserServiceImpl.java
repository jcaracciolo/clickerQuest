package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MarketDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MarketDao marketDao;

    Cache<String, User> userCache = CacheBuilder.newBuilder().maximumSize(5).build();
    Cache<Long, Wealth> wealthCache = CacheBuilder.newBuilder().maximumSize(5).build();

    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            user = userCache.get(username,()-> userDao.findByUsername(username));
        } catch (CacheLoader.InvalidCacheLoadException e) {
            LOGGER.error(e.toString());
        } catch (ExecutionException e) {
            LOGGER.error(e.toString());
        }
        return user;
    }

    @Override
    public Wealth getUserWealth(long userid) {

        Wealth wealth = null;

        try {
            return wealthCache.get(userid, () -> {
                Wealth w = userDao.getUserWealth(userid);
                Map<ResourceType,Double> storageRaw = w.getStorage().rawMap();

                if(storageRaw.size() < ResourceType.values().length) {
                    PackageBuilder<Storage> storageB = Storage.packageBuilder();
                    PackageBuilder<Productions> productionsB = Productions.packageBuilder();
                    Calendar now = Calendar.getInstance();

                    Stream.of(ResourceType.values()).filter(
                            resType -> storageRaw.keySet().stream().noneMatch(r -> r == resType))
                            .forEach(   resType -> {
                                storageB.putItem(resType,0D);
                                productionsB.putItem(resType,0D);
                            });

                    w.getStorage().rawMap().forEach(storageB::putItem);
                    w.getProductions().rawMap().forEach(productionsB::putItem);
                    Stream.of(ResourceType.values()).forEach((r) -> storageB.setLastUpdated(r,now));
                    updateWealth(new Wealth(userid,storageB.buildPackage(),productionsB.buildPackage()));

                    return userDao.getUserWealth(userid);
                }
                return w;
            });
        } catch (CacheLoader.InvalidCacheLoadException e) {
            LOGGER.error(e.toString());
        } catch (ExecutionException e) {
            LOGGER.error(e.toString());
        }
        return wealth;
    }

    public User create(String username, String password, String img) {
        User user = userDao.create(username,passwordEncoder.encode(password),img);
        if(user != null){

            for (FactoryType type: FactoryType.values()){
                Factory factory = type.defaultFactory(user.getId());
                create(factory.getType(),user.getId());
            }

            updateWealth(createWealth(user.getId()));

            purchaseFactory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE);
            purchaseFactory(user.getId(),FactoryType.STOCK_INVESTMENT_BASE);
        }
        return user;
    }

    @Override
    public Productions getUserProductions(long id) {
        return userDao.getUserProductions(id);
    }

    @Override
    public Storage getUserStorage(long id) {
        return userDao.getUserStorage(id);
    }

    @Override
    public boolean purchaseFactory(long userid, FactoryType type) {
        Wealth w = getUserWealth(userid);
        Optional<Factory> maybeFactory = userDao.getUserFactories(userid).stream()
                .filter( (f) -> f.getType() == type).findAny();

        Factory f;

        if(maybeFactory.isPresent()) {
            f = maybeFactory.get();
        } else {
            return false;
        }

        if( f.isBuyable(w)) {
            Wealth wealth = w.purchaseResult(f);
            Factory factory = f.purchaseResult();

            wealth = updateWealth(wealth);
            factory = userDao.update(factory);

            return factory != null && wealth != null;
        }
        return false;
    }

    @Override
    public Collection<Factory> getUserFactories(long userid) {
        final Collection<Factory> factories = userDao.getUserFactories(userid);
        if(factories.size() < FactoryType.values().length){
            List<Factory> lostFactories = Stream.of(FactoryType.values())
                    .filter((t) -> factories.stream().noneMatch((f) -> f.getType() == t))
                    .map(
                            (t) -> create(t,userid)
                    )
                    .collect(Collectors.toList());

            lostFactories.addAll(factories);
            return lostFactories;
        }
        return factories;
    }

    private Factory create(FactoryType factoryType, long userId){
        final Factory f = factoryType.defaultFactory(userId);
        userDao.create(f,userId);
        return f;
    }

    @Override
    public String getProfileImage(long userid) {
        return userDao.getProfileImage(userid);
    }

    @Override
    public boolean purchaseUpgrade(long userid, FactoryType type) {
        Collection<Factory> factories = getUserFactories(userid);
        Factory factory = factories.stream()
                .filter(
                        (f) -> f.getType().getId() == type.getId()
                ).findAny().get();

        Wealth w = getUserWealth(userid);

        if(factory.isUpgreadable(w)) {
            Factory newFactory = factory.upgradeResult();
            factories.remove(factory);
            factories.add(newFactory);
            Wealth newWealth = w.calculateProductions(factories);
            newWealth = newWealth.addResource(ResourceType.MONEY,-factory.getNextUpgrade().getCost());
            userDao.update(newFactory);
            updateWealth(newWealth);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean sellResourceType(long userid, ResourceType resourceType, double amount) {
        if(resourceType == ResourceType.MONEY) {
            return false;
        }

        Wealth wealth = getUserWealth(userid);
        double cost = (resourceType.getPrice()) * amount;
        if( wealth.getStorage().getValue(resourceType) <  amount ) {
            return false;
        }

        PackageBuilder<Storage> wbuilder = Storage.packageBuilder();
        wealth.getStorage().rawMap().forEach(
                (r,d) -> wbuilder.putItem(r,d)
        );
        wealth.getStorage().getLastUpdated().forEach(
                (r,d) -> wbuilder.setLastUpdated(r,d)
        );

        wbuilder.addItem(resourceType,-amount);
        wbuilder.addItem(ResourceType.MONEY,cost);

        Wealth newWealth = new Wealth(userid,wbuilder.buildPackage(),wealth.getProductions());
        updateWealth(newWealth);
        marketDao.registerPurchase(new StockMarketEntry(userid,resourceType,-amount));

        return true;
    }

    private boolean setResourceStorage(long userid, ResourceType resourceType, double amount) {
        Wealth wealth = getUserWealth(userid);

        PackageBuilder<Storage> wbuilder = Storage.packageBuilder();
        wealth.getStorage().rawMap().forEach(
                (r,d) ->{
                    if(r.equals(resourceType)){
                        wbuilder.putItem(r,amount);
                    }else  {
                        wbuilder.putItem(r,d);
                    }
                }
        );
        wealth.getStorage().getLastUpdated().forEach(
                (r,d) -> wbuilder.setLastUpdated(r,d)
        );

        Wealth newWealth = new Wealth(userid,wbuilder.buildPackage(),wealth.getProductions());
        updateWealth(newWealth);
        return true;
    }

    @Override
    public boolean purchaseResourceType(long userid, ResourceType resourceType,double amount){
        Wealth wealth = getUserWealth(userid);
        double cost = (resourceType.getPrice()) * amount;
        if( wealth.getStorage().getValue(ResourceType.MONEY) <  cost ) {
            return false;
        }

        PackageBuilder<Storage> wbuilder = Storage.packageBuilder();
        wealth.getStorage().rawMap().forEach(
                (r,d) -> wbuilder.putItem(r,d)
        );
        wealth.getStorage().getLastUpdated().forEach(
                (r,d) -> wbuilder.setLastUpdated(r,d)
        );

        wbuilder.addItem(ResourceType.MONEY,-cost);
        wbuilder.addItem(resourceType,amount);

        Wealth newWealth = new Wealth(userid,wbuilder.buildPackage(),wealth.getProductions());
        updateWealth(newWealth);
        marketDao.registerPurchase(new StockMarketEntry(userid,resourceType,amount));

        return true;
    }

    private Wealth createWealth(long userId) {
        PackageBuilder<Storage> storageBuilder = Storage.packageBuilder();
        PackageBuilder<Productions> productionsBuilder = Productions.packageBuilder();
        Calendar now = Calendar.getInstance();

        Arrays.stream(ResourceType.values()).forEach((r) ->  {
            storageBuilder.putItemWithDate(r,0D,now);
            productionsBuilder.putItem(r,0D);
        });

        storageBuilder.addItem(ResourceType.MONEY,ResourceType.initialMoney());
        return new Wealth(userId,storageBuilder.buildPackage(),productionsBuilder.buildPackage());
    }

    private Wealth updateWealth(Wealth wealth){
        long userId = wealth.getUserid();
        wealthCache.put(userId,wealth);
        User oldUser = findById(userId);
        User newUser = new User(oldUser.getId(),
                                oldUser.getUsername(),
                                oldUser.getPassword(),
                                oldUser.getProfileImage(),
                                wealth.calculateScore(),
                                oldUser.getClanIdentifier());
        if(userDao.update(newUser) != null) {
            userCache.put(newUser.getUsername(),newUser);
            return userDao.update(wealth);
        } else {
            return null;
        }
    }

    @Override
    public int getGlobalRanking(long userId) {
        return userDao.getGlobalRanking(userId);
    }
}
