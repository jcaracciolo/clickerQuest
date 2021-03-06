package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ClanDao;
import ar.edu.itba.paw.interfaces.ClanService;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.model.clan.ClanBattle;
import ar.edu.itba.paw.model.packages.Paginating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by juanfra on 17/05/17.
 */
@Service
@Transactional
public class ClanServiceImpl implements ClanService {

    @Autowired
   private ClanDao clanDao;

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
    public Collection<String> findByKeyword(String search) {
        if(!search.matches("^[a-zA-Z0-9]+$")) return Collections.emptyList();
        return clanDao.findByKeyword(search);
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
    @Transactional(propagation = Propagation.NESTED)
    public boolean deleteFromClan(long userId) {
        return clanDao.removeFromClan(userId);
    }

    @Override
    public Paginating<Clan> globalClans(int page, int clansPerPage) {
        return clanDao.globalClan(page,clansPerPage);
    }

    @Override
    public Integer getGlobalRanking(int clanid) {
        return clanDao.getGlobalRanking(clanid);
    }

    @Override
    public ClanBattle getClanBattle(int clanid) {
        return clanDao.getClanBattle(clanid);
    }

    @Scheduled(fixedDelay = 1*60*1000)
    protected void calculateNextBattles(){
        clanDao.calculateNextBattles();
    }
}
