package ar.edu.itba.paw.services.MockData;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public ResourcePackage getUserProductions(long id) {
        Map<Resources,Double> productions = new HashMap<>();
        Random rng = new Random();
        for (Resources r:Resources.values()) {
            productions.put(r,rng.nextDouble()*15);
        }
        return new ResourcePackage(productions);
    }

    @Override
    public ResourcePackage getUserStorage(long id) {
        Map<Resources,Double> storage = new HashMap<>();
        Random rng = new Random();
        for (Resources r:Resources.values()) {
            storage.put(r,Math.floor(rng.nextDouble()*1000));
        }
        return new ResourcePackage(storage);
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
