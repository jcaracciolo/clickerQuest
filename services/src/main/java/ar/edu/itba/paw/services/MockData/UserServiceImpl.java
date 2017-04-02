package ar.edu.itba.paw.services.MockData;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by juanfra on 23/03/17.
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserDao userDao;

    public User findById(long id) {
        return new User(id,"JuanFra","pass","1.jpg");
    }

    public User create(String username, String Password, String img) {
        return findById(1);
    }

    @Override
    public List<Production> getUserProductions(long id) {
        List<Production> productions = new ArrayList<>();
        Random rng = new Random();
        for (Resources r:Resources.values()) {
            productions.add(new Production(r,rng.nextDouble()*10));
        }
        return productions;
    }

    @Override
    public List<Amount> getUserStorage(long id) {
        List<Amount> storage = new ArrayList<>();
        Random rng = new Random();
        for (Resources r:Resources.values()) {
            storage.add(new Amount(r,rng.nextInt(10000)));
        }
        return storage;
    }

    @Override
    public List<Factory> getUserFactories(long id) {
        List<Factory> factories = new ArrayList<>();
        Random rng = new Random();
        for (FactoryType f:FactoryType.values()) {
            int amount = rng.nextInt(10)>7 ? 0 : rng.nextInt(1000);
            factories.add(new Factory(id,f,amount,1,1,1,0));
        }
        return factories;
    }

}
