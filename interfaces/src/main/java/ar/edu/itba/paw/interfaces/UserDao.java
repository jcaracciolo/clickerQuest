package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

/**
 * Created by juanfra on 23/03/17.
 */
public interface UserDao {

    User findById(long id);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @return The created user.
     */
    User create(String username);
}
