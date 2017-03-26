package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.PublicUser;
import ar.edu.itba.paw.model.User;

/**
 * Created by juanfra on 23/03/17.
 */
public interface UserDao {

    PublicUser findById(long id);

//    User validateToken (String token, long id );

//    User login(String username, String password);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @return The created user.
     */
    User create(String username, String password);
}
