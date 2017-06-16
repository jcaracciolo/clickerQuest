package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.Paginating;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        User u = findById(factory.getUserid());
        em.persist(factory);
        u.getFactories().add(factory);
        update(u);
        return factory;
    }

    @Override
    public Wealth create(Wealth w) {
        if(w.getStorage().rawMap().isEmpty() || w.getProductions().rawMap().isEmpty()) {
            return null;
        }
        em.persist(w);
        update(w);
        return w;
    }

    @Override
    public String getProfileImage(long userid) {
        return findById(userid).getProfileImage();
    }

    @Override
    public Productions getUserProductions(long userid) {
        return getUserWealth(userid).getProductions();
    }

    @Override
    public Storage getUserStorage(long userid) {
        return getUserWealth(userid).getStorage();
    }

    @Override
    public User update(User u) {
        if(u.getFactories().isEmpty()){
            u.setFactories(getUserFactories(u.getId()));
        }
        return em.merge(u);
    }

    @Override
    public Collection<Factory> getUserFactories(long userid) {
        User u =  findById(userid);
        Collection<Factory> f = u.getFactories();
        return findById(userid).getFactories();
    }

    @Override
    public Factory update(Factory f) {
        User u = findById(f.getUserid());
        u.getFactories().remove(f);
        u.getFactories().add(f);
        return f;
    }

    @Override
    public Wealth getUserWealth(long userid) {
        return findById(userid).getWealth();
    }

    @Override
    public Wealth update(Wealth w) {
        if(w.getStorage().rawMap().isEmpty() || w.getProductions().rawMap().isEmpty()) {
            return null;
        }
        User u = findById(w.getUserid());
        u.setWealth(w);
        em.merge(w);
        return w;
    }

    @Override
    public Paginating<User> globalUsers(int pag, int userPerPage) {
        return null;
    }

}
