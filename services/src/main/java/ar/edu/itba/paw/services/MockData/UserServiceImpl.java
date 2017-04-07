package ar.edu.itba.paw.services.MockData;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.packages.*;
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
        return userDao.findById(id);
    }

    public User create(String username, String password, String img) {
        return userDao.create(username,password,"1.jpg");
    }

    @Override
    public Production getUserProductions(long id) {
        return userDao.getUserProductions(id);
    }

    @Override
    public Storage getUserStorage(long id) {
        return userDao.getUserStorage(id);
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
