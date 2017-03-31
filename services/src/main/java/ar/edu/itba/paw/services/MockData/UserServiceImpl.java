package ar.edu.itba.paw.services.MockData;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
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
        List<Amount> storage = new ArrayList<>();
        List<Production> productions = new ArrayList<>();
        Random rng = new Random();

        for (Resources r: Resources.values()) {
            storage.add(new Amount(r, Math.floor(rng.nextDouble()*1000)));
            productions.add(new Production(r,rng.nextDouble()*10));
        }

        Resources.values()
        return new User(id,"Juanfra" + id,"pass","profile.jpg",storage,productions);
    }


    public User create(String username, String Password, String img) {
        return findById(1);
    }

    public List<Factory> getFactoriesforUser(long id) {
        List<Factory> userFactories = new ArrayList<>();
        for (Factories f: Factories.values()) {
            Factory factory = new Factory(f,);
        }
    }


}
