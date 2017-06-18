package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ClanDao;
import ar.edu.itba.paw.model.clan.Clan;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by juanfra on 18/06/17.
 */
@Repository
public class MockClanDao implements ClanDao {
    @Override
    public Clan createClan(String name) {
        return null;
    }

    @Override
    public Clan getClanByName(String name) {
        return null;
    }

    @Override
    public Clan getClanById(int clanId) {
        return null;
    }

    @Override
    public Collection<String> findByKeyword(String search) {
        return null;
    }

    @Override
    public boolean addToClan(int clanId, long userdId) {
        return false;
    }

    @Override
    public boolean removeFromClan(long userId) {
        return false;
    }
}
