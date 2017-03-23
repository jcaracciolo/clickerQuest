package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by juanfra on 23/03/17.
 */
@Repository
public class UserDaoImpl implements UserDao {

    public User findById(int id) {
        return new User("JuanFra",id);
    }

}
