package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by juanfra on 23/03/17.
 */
@Service
public class UserServiceImpl implements UserService {

    public User findById(int id) {
        return new User("JuanFra",id);
    }
}
