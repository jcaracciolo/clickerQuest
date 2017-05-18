package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ClanDao;
import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.model.clan.Clan;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by juanfra on 17/05/17.
 */
public class ClanServiceImpl implements ClanService {

    @Autowired
    ClanDao clanDao;

    @Override
    public Clan createClan(String name) {
        return clanDao.createClan(name);
    }

    @Override
    public Clan getClanByName(String name) {
        return clanDao.getClanByName(name);
    }

    @Override
    public Clan getClanById(int clanId) {
        return clanDao.getClanById(clanId);
    }

    @Override
    public Clan addUserToClan(int clanId, long userId) {
        if( clanDao.addToClan(clanId,userId) ) {
            return getClanById(clanId);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteFromClan(long userId) {
        return clanDao.removeFromClan(userId);
    }
}
