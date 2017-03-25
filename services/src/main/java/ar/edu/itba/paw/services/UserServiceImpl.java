package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by juanfra on 23/03/17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public User findById(long id) {
        return new User("JuanFra",id);
    }

    public User create(String username) {
        return userDao.create(username);
    }
}
