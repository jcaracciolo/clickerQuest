package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Amount;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.Production;
import ar.edu.itba.paw.model.User;

import java.util.List;

/**
 * Created by juanfra on 23/03/17.
 */
public interface UserService {

    User findById(long id);
    User create(String username, String Password, String img);
    List<Production> getUserProductions(long id);
    List<Amount> getUserStorage(long id);
    List<Factory> getUserFactories(long id);

}
