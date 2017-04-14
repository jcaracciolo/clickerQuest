package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

}
