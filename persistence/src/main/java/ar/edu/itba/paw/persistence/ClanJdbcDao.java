package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ClanDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.Factory;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.clan.Clan;
import ar.edu.itba.paw.model.clan.ClanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juanfra on 17/05/17.
 */
@Repository
public class ClanJdbcDao implements ClanDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertClans;

    @Autowired
    private UserJdbcDao userDao;

    private static class RowClan {
        int id;
        String name;

        public RowClan(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private final static RowMapper<RowClan> CLAN_ROW_MAPPER = (rs, rowNum) ->
            new RowClan(rs.getInt("clanId"),
                    rs.getString("name")
            );

    @Autowired
    public ClanJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);

        jdbcInsertClans = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("clans")
                .usingGeneratedKeyColumns("clanId");
    }

    @Override
    public Clan createClan(String name) {
        if(name==null) {
            return null;
        }

        final Map<String, Object> args = new HashMap();
        args.put("name",       name);

        Number id = jdbcInsertClans.executeAndReturnKey(args);
        if(id==null){
            return null;
        }

        return ClanBuilder.construct(id.intValue(),name).buildClan();
    }

    @Override
    public Clan getClanByName(String name) {
        final List<RowClan> list = jdbcTemplate.query("SELECT * FROM clans WHERE name = ?", CLAN_ROW_MAPPER, name);
        if (list.isEmpty()) {
            return null;
        }
        RowClan rowClan = list.get(0);
        List<User> users = getClanUsers(rowClan.id);
        ClanBuilder clanBuilder = ClanBuilder.construct(rowClan.id,name);
        users.forEach(clanBuilder::add);

        return clanBuilder.buildClan();
    }

    @Override
    public Clan getClanById(int clanId) {
        final List<RowClan> list = jdbcTemplate.query("SELECT * FROM clans WHERE clanId = ?", CLAN_ROW_MAPPER, clanId);
        if (list.isEmpty()) {
            return null;
        }
        RowClan rowClan = list.get(0);
        List<User> users = getClanUsers(clanId);
        ClanBuilder clanBuilder = ClanBuilder.construct(rowClan.id,rowClan.name);
        users.forEach(clanBuilder::add);

        return clanBuilder.buildClan();
    }

    @Override
    public boolean addToClan(int clanId, long userId) {
        User user = userDao.findById(userId);
        if(user == null) {
            return false;
        }

        if(user.getClanIdentifier() != null) {
            return false;
        }

        final List<RowClan> list = jdbcTemplate.query("SELECT * FROM clans WHERE clanId = ?", CLAN_ROW_MAPPER, clanId);
        if (list.isEmpty()) {
            return false;
        }

        RowClan rowClan = list.get(0);

        User newUser = new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getProfileImage(),
                user.getScore(),
                rowClan.id);

        return userDao.update(newUser)!=null;
    }

    @Override
    public boolean removeFromClan(long userId) {
        User user = userDao.findById(userId);
        if(user==null) {
            return false;
        }
        User newUser = new User(userId,user.getUsername(),user.getPassword(),user.getProfileImage(),
                                user.getScore(),
                                null);
        return userDao.update(newUser) != null;
    }

    private List<User> getClanUsers(int clanId){
        final List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE clanId = ?", UserJdbcDao.USER_ROW_MAPPER,clanId);
        if(users.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return users;
    }
}
