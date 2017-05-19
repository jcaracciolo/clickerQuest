package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.clan.Clan;

/**
 * Created by juanfra on 17/05/17.
 */
public interface ClanDao {
    Clan createClan(String name);
    Clan getClanByName(String name);
    Clan getClanById(int clanId);
    boolean addToClan(int clanId, long userdId);
    boolean removeFromClan(long userId);
}
