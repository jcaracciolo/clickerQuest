package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

/**
 * Created by juanfra on 23/03/17.
 */
public interface UserService {

    User findById(long id);
    User create(String username, String Password, String img);


}
