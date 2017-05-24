package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ClanDao;
import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.model.clan.Clan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by juanfra on 17/05/17.
 */
@Service
@Lazy
public class ClanServiceImpl implements ClanService {

    @Autowired
    ClanDao clanDao;

    @Override
    public Clan createClan(String name) {
        return clanDao.createClan(name);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Clan getClanByName(String name) {
        return clanDao.getClanByName(name);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Clan getClanById(int clanId) {
        return clanDao.getClanById(clanId);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Collection<String> findByKeyword(String search) {
        if(!search.matches("^[a-zA-Z0-9]+$")) return Collections.emptyList();
        Collection<String> asd = clanDao.findByKeyword(search);
        return asd;
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public Clan addUserToClan(int clanId, long userId) {
        if( clanDao.addToClan(clanId,userId) ) {
            return getClanById(clanId);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public boolean deleteFromClan(long userId) {
        return clanDao.removeFromClan(userId);
    }


}
