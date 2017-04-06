package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Production;
import ar.edu.itba.paw.model.packages.ResourcePackage;
import ar.edu.itba.paw.model.packages.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by juanfra on 23/03/17.
 */
@Repository
public class UserJdbcDao implements UserDao {

    private static class RowWealth {
        long userid;
        ResourceType resourceType;
        double production;
        double storage;
        Date lastUpdated;

        public RowWealth(long userid, ResourceType resourceType, double production, double storage, Date lastUpdated) {
            this.userid = userid;
            this.resourceType = resourceType;
            this.production = production;
            this.storage = storage;
            this.lastUpdated = lastUpdated;
        }

        static Collection<RowWealth> getRowWealths(Wealth w){
            List<RowWealth> rows = new ArrayList<>();
            for (ResourceType r:ResourceType.values()) {
                rows.add(new RowWealth(w.userid,r,
                        w.getProductions().getValue(r),
                        w.getStorage().getValue(r),
                        new Date()));
            }
            return rows;
        }
    }

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertUsers;
    private final SimpleJdbcInsert jdbcInsertFactories;
    private final SimpleJdbcInsert jdbcInsertWealths;

    //region ROWMAPPER
    private final static RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
            new User(rs.getLong("userid"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("profileImage"));

    private final static ReverseRowMapper<User> USER_REVERSE_ROW_MAPPER = (us) ->
    {
        final Map<String, Object> args = new HashMap();
        args.put("username", us.getUsername());
        args.put("password", us.getPassword());
        args.put("profileImage", us.getProfileImage());
        return args;
    };

    private final static RowMapper<Factory> FACTORY_ROW_MAPPER = (rs, rowNum) ->
            new Factory(rs.getLong("userid"),
                    FactoryType.fromId(rs.getInt("type")),
                    rs.getDouble("amount"),
                    rs.getDouble("inputReduction"),
                    rs.getDouble("outputMultiplier"),
                    rs.getDouble("costReduction"),
                    Upgrade.getUpgrade(FactoryType.fromId(rs.getInt("type")),rs.getInt("level"))
                    );

    private final static ReverseRowMapper<Factory> FACTORY_REVERSE_ROW_MAPPER = (f) ->
    {
        final Map<String, Object> args = new HashMap();
        args.put("userid", f.getUserid());
        args.put("type", f.getType().getId());
        args.put("amount", f.getAmount());
        args.put("inputReduction", f.getInputReduction());
        args.put("outputMultiplier", f.getOutputMultiplier());
        args.put("costReduction", f.getCostReduction());
        args.put("level", f.getUpgrade().getLevel());
        return args;

    };

    private final static RowMapper<RowWealth> WEALTH_ROW_MAPPER = (rs, rowNum) ->
            new RowWealth(rs.getLong("userid"),
                    ResourceType.fromId(rs.getInt("resourceType")),
                    rs.getDouble("production"),
                    rs.getDouble("storage"),
                    rs.getDate("lastUpdated"));

    private final static ReverseRowMapper<RowWealth> WEALTH_REVERSE_ROW_MAPPER = (rw) ->
    {
        final Map<String, Object> args = new HashMap();
        args.put("userid", rw.userid);
        args.put("resourceType", rw.resourceType);
        args.put("production",rw.production);
        args.put("storage", rw.storage);
        args.put("lastUpdated", rw.lastUpdated);
        return args;

    };
    //endregion


    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);

        jdbcInsertUsers = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("userid");

        jdbcInsertFactories = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("factories");

        jdbcInsertWealths = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("wealths");
    }

    //region Update
    public User create(String username, String password,String img) {
        final User us = new User(0,username,password,img);

        final Number userId = jdbcInsertUsers.executeAndReturnKey(USER_REVERSE_ROW_MAPPER.toArgs(us));

        for (FactoryType type: FactoryType.values()){
            final Factory f = new Factory(userId.longValue(),type,0,1,1,1,Upgrade.getUpgrade(type,0));
            jdbcInsertFactories.execute(FACTORY_REVERSE_ROW_MAPPER.toArgs(f));
        }

        for (ResourceType rt: ResourceType.values()) {
            final RowWealth rw = new RowWealth(userId.longValue(),rt,0D,0D,new Date());
            jdbcInsertWealths.execute(WEALTH_REVERSE_ROW_MAPPER.toArgs(rw));

        }

        return new User(userId.longValue(), username,password,img);
    }


    //endregion

    //region Retrieval
    public User findById(final long userid) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", USER_ROW_MAPPER, userid);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    //TODO make a correct implementation
    public String getProfileImage(final long userid) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", USER_ROW_MAPPER, userid);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0).getProfileImage();
    }

    @Override
    public Production getUserProductions(long userid) {
        return getUserWealth(userid).getProductions();
    }

    @Override
    public Storage getUserStorage(long userid) {
        return getUserWealth(userid).getStorage();
    }

    @Override
    public Collection<Factory> getUserFactories(long userid) {
        return jdbcTemplate.query("SELECT * FROM factories WHERE userid = ?", FACTORY_ROW_MAPPER, userid);
    }

    @Override
    public Wealth getUserWealth(long userid) {
        final List<RowWealth> list = jdbcTemplate.query("SELECT * FROM factories WHERE userid = ?", WEALTH_ROW_MAPPER, userid);
        Map<ResourceType,Double> storage = new HashMap<>();
        Map<ResourceType,Double> productions = new HashMap<>();
        Map<ResourceType,Calendar> lastUpdated = new HashMap<>();
        for (RowWealth rw: list) {
            storage.put(rw.resourceType,rw.storage);
            productions.put(rw.resourceType,rw.production);
            lastUpdated.put(rw.resourceType,toCalendar(rw.lastUpdated));
        }

        Production production = new Production(new ResourcePackage(productions));
        Storage st = new Storage(new ResourcePackage(storage),lastUpdated);

        return new Wealth(userid,Calendar.getInstance(),st,production);
    }
    //endregion

    private static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
