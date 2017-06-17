package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ClanDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.model.clan.ClanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by juanfra on 14/06/17.
 */
@Repository
public class HibernateClanDao implements ClanDao {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userdao;

    @Override
    public Clan createClan(String name) {
        Clan clan = ClanBuilder.construct(0,name).buildClan();
        em.persist(clan);
        return clan;
    }

    @Override
    public Clan getClanByName(String name) {
        final TypedQuery<Clan> query = em.createQuery( "from Clan as c where c.name = :name" , Clan.class);
        query.setParameter( "name" , name);
        final List<Clan> list = query.getResultList();
        return list.size()!=1 ? null : list.get( 0 );
    }

    @Override
    public Clan getClanById(int clanId) {
        final TypedQuery<Clan> query = em.createQuery( "from Clan as c where c.id = :clanid" , Clan.class);
        query.setParameter( "clanid" , clanId);
        final List<Clan> list = query.getResultList();
        return list.size()!=1 ? null : list.get( 0 );
    }

    @Override
    public Collection<String> findByKeyword(String search) {
        StringBuilder s = new StringBuilder(search.toLowerCase()).append("%").insert(0,"%");
        final TypedQuery<Clan> query = em.createQuery( "from Clan as c where lower(c.name) like lower(:name)" , Clan.class);
        query.setParameter( "name" , s.toString());
        final List<Clan> list = query.getResultList();
        return list.stream().map(Clan::getName).collect(Collectors.toList());
    }

    @Override
    public boolean addToClan(int clanId, long userId) {
        Clan clan = getClanById(clanId);
        User u = userdao.findById(userId);
        if(clan == null || u.getClanId()!=null){
            return false;
        }

        u.setClanId(clanId);
        clan.getUsers().add(u);
        return true;

    }

    @Override
    public boolean removeFromClan(long userId) {
        User u = userdao.findById(userId);
        if(u.getClanId()==null) {
            return false;
        }

        Clan clan = getClanById(u.getClanId());
        if(clan == null){
            return false;
        }

        u.setClanId(null);
        Iterator<User> it = clan.getUsers().iterator();
        while (it.hasNext()){
            if(it.next().getId() == u.getId()){
                it.remove();
                break;
            }
        }

        if(clan.getUsers().isEmpty()){
            em.remove(clan);
        }

        return true;
    }
}
