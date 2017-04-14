package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.SingleProduction;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        return userDao.create(username,password,img);
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
        return userDao.getUserFactories(id);
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

        Upgrade toBuy = factory.getNextUpgrade();

        Wealth w = getUserWealth(userid);

        if(toBuy.isBuyable(w)) {
            Factory newFactory = factory.purchaseResult(toBuy);

            factories.remove(factory);
            factories.add(newFactory);
            Collection<SingleProduction> singleProductions =
                    factories.stream().map(Factory::getSingleProduction).collect(Collectors.toList());

            Wealth newWealth = w.purchaseResult(toBuy,singleProductions);

            userDao.update(newFactory);
            userDao.update(newWealth);
            return true;
        } else {
            return false;
        }



    }
}
