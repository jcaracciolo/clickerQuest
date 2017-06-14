package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.Paginating;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Repository
public class UserHibernateDao implements UserDao{
    @PersistenceContext
    private EntityManager em;


    @Override
    public User findByUsername (final String username) {
        final TypedQuery<User> query = em.createQuery( "from User as u where u.username = :username" , User.class);
        query.setParameter( "username" , username);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? null : list.get( 0 );
    }

    @Override
    public Collection<User> findByKeyword(String search) {
        StringBuilder s = new StringBuilder(search.toLowerCase()).append("%").insert(0,"%");
        final TypedQuery<User> query = em.createQuery( "from User as u where lower(u.username) like lower(:username)" , User.class);
        query.setParameter( "username" , s.toString());
        final List<User> list = query.getResultList();
        return list;
    }

    @Override
    public Integer getGlobalRanking(long userId) {
        return null;
    }

    @Override
    public User findById ( long id){
         return em.find(User.class, id);
     }

    @Override
    public User create(String username, String password, String profileImage) {
        final User user = new User(username, password,profileImage,0,null);
        em.persist(user);
        return user;
    }

    @Override
    public Factory create(Factory factory) {
        return null;
    }

    @Override
    public Wealth create(Wealth wealth) {
        return null;
    }

    @Override
    public String getProfileImage(long userid) {
        return null;
    }

    @Override
    public Productions getUserProductions(long userid) {
        return null;
    }

    @Override
    public Storage getUserStorage(long userid) {
        return null;
    }

    @Override
    public User update(User u) {
        return null;
    }

    @Override
    public Collection<Factory> getUserFactories(long userid) {
        return null;
    }

    @Override
    public Factory update(Factory f) {
        return null;
    }

    @Override
    public Wealth getUserWealth(long userid) {
        return null;
    }

    @Override
    public Wealth update(Wealth w) {
        return null;
    }

    @Override
    public Paginating<User> globalUsers(int pag, int userPerPage) {
        return null;
    }

}
