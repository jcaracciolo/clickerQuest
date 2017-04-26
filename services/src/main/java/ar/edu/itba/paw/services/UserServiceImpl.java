package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by juanfra on 23/03/17.
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserDao userDao;

    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User create(String username, String password, String img) {
        User user = userDao.create(username,password,img);
        purchaseFactory(user.getId(),FactoryType.PEOPLE_RECRUITING_BASE);
        purchaseFactory(user.getId(),FactoryType.STOCK_INVESTMENT_BASE);
        return user;
    }

    @Override
    public Wealth getUserWealth(long id) {
        return userDao.getUserWealth(id);
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
        Factory f = userDao.getUserFactory(userid,type);
        if( f.isBuyable(w)) {
            Wealth wealth = w.purchaseResult(f);
            Factory factory = f.purchaseResult();

            wealth = userDao.update(wealth);
            factory = userDao.update(factory);

            return factory != null && wealth != null;
        }
        return false;
    }

    @Override
    public Collection<Factory> getUserFactories(long id) {
//        Collection<Factory> factories = userDao.getUserFactories(id);
        Collection<Factory> factories = new ArrayList<>();
        Stream.of(FactoryType.values()).forEach(type -> factories.add(userDao.getUserFactory(id,type)));
        return factories;
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
            Wealth newWealth = w.upgradeResult(factory);

            userDao.update(newFactory);
            userDao.update(newWealth);
            return true;
        } else {
            return false;
        }



    }
}
