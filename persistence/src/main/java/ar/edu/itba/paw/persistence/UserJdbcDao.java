package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class UserJdbcDao implements UserDao {

    private static class RowWealth {
        long userid;
        ResourceType resourceType;
        double production;
        double storage;
        long lastUpdated;

        public RowWealth(long userid, ResourceType resourceType, double production, double storage, long lastUpdated) {
            this.userid = userid;
            this.resourceType = resourceType;
            this.production = production;
            this.storage = storage;
            this.lastUpdated = lastUpdated;
        }

        static Collection<RowWealth> getRowWealths(Wealth w){
            List<RowWealth> rows = new ArrayList<>();
            for (ResourceType r:ResourceType.values()) {
                rows.add(new RowWealth(w.getUserid(),r,
                        w.getProductions().getValue(r),
                        w.getStorage().getValue(r),
                        w.getStorage().getLastUpdated(r).getTimeInMillis()));
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
        args.put("username",        us.getUsername());
        args.put("password",        us.getPassword());
        args.put("profileImage",    us.getProfileImage());
        return args;
    };

    private final static RowMapper<Factory> FACTORY_ROW_MAPPER = (rs, rowNum) ->
            new Factory(rs.getLong("userid"),
                    FactoryType.fromId(rs.getInt("type")),
                    rs.getDouble("amount"),
                    rs.getDouble("inputReduction"),
                    rs.getDouble("outputMultiplier"),
                    rs.getDouble("costReduction"),
                    rs.getInt("level")
                    );

    private final static ReverseRowMapper<Factory> FACTORY_REVERSE_ROW_MAPPER = (f) ->
    {
        final Map<String, Object> args = new HashMap();
        args.put("userid",              f.getUserid());
        args.put("type",                f.getType().getId());
        args.put("amount",              f.getAmount());
        args.put("inputReduction",      f.getInputReduction());
        args.put("outputMultiplier",    f.getOutputMultiplier());
        args.put("costReduction",       f.getCostReduction());
        args.put("level",               f.getLevel());
        return args;

    };

    private final static RowMapper<RowWealth> WEALTH_ROW_MAPPER = (rs, rowNum) ->
            new RowWealth(rs.getLong("userid"),
                    ResourceType.fromId(rs.getInt("resourceType")),
                    rs.getDouble("production"),
                    rs.getDouble("storage"),
                    rs.getLong("lastUpdated"));

    private final static ReverseRowMapper<RowWealth> WEALTH_REVERSE_ROW_MAPPER = (rw) ->
    {
        final Map<String, Object> args = new HashMap();
        args.put("userid",          rw.userid);
        args.put("resourceType",    rw.resourceType.getId());
        args.put("production",      rw.production);
        args.put("storage",         rw.storage);
        args.put("lastUpdated",     rw.lastUpdated);
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
        final Number userId;

        try {
             userId = jdbcInsertUsers.executeAndReturnKey(USER_REVERSE_ROW_MAPPER.toArgs(us));
        }catch (Exception e){
            return null;
        }

        for (FactoryType type: FactoryType.values()){
            Factory factory = type.defaultFactory(userId.longValue());
            create(factory,userId.longValue());
        }

        for (ResourceType rt: ResourceType.values()) {
            create(rt,userId.longValue());
        }

        return new User(userId.longValue(), username,password,img);
    }

    public Factory update(Factory f) {
        int rows = jdbcTemplate.update(
                    "UPDATE factories SET " +
                            "amount = ?," +
                            "inputReduction = ?," +
                            "outputMultiplier = ?," +
                            "costReduction = ?," +
                            "level = ?" +
                            " WHERE (userid = ?) AND (type = ?);",
                    f.getAmount(),
                    f.getInputReduction(),
                    f.getOutputMultiplier(),
                    f.getCostReduction(),
                    f.getLevel(),
                    f.getUserid(),
                    f.getType().getId());

        if(rows == 1) {
            return f;
        } else if (rows == 0) {
            //TODO log no update
           return null;
        } else {
            //TODO multiple updates
            return null;
        }
    }

    public Wealth update(Wealth w) {
        Storage s = w.getStorage();
        Productions p = w.getProductions();
        for(ResourceType r: ResourceType.values()) {
            int rows = jdbcTemplate.update(
                    "UPDATE wealths SET " +
                            "production = ?," +
                            "storage = ?," +
                            "lastUpdated = ?" +
                            " WHERE (userid = ?) AND (resourceType = ?);",
                    p.getValue(r),
                    s.getValue(r),
                    s.getLastUpdated(r).getTimeInMillis(),
                    w.getUserid(),
                    r.getId());

            if (rows == 0) {
                //TODO log no update
                return null;
            } else if(rows >1) {
                //TODO multiple updates
                return null;
            }
        }

        return w;

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

    @Override
    public User findByUsername(String username) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE username = ?", USER_ROW_MAPPER, username);
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
    public Productions getUserProductions(long userid) {
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

    public Wealth getUserWealth(long userid) {
        final List<RowWealth> list = jdbcTemplate.query("SELECT * FROM wealths WHERE userid = ?", WEALTH_ROW_MAPPER, userid);
        PackageBuilder<Storage> storage = Storage.packageBuilder();
        PackageBuilder<Productions> productions = Productions.packageBuilder();
        for (RowWealth rw: list) {
            storage.putItemWithDate(rw.resourceType,rw.storage,toCalendar(rw.lastUpdated));
            productions.putItem(rw.resourceType,rw.production);
        }

        return new Wealth(userid,storage.buildPackage(),productions.buildPackage());
    }

    private static Calendar toCalendar(long milis){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milis);
        return cal;
    }

    public Factory create(Factory factory, long userId){
        try {
            jdbcInsertFactories.execute(FACTORY_REVERSE_ROW_MAPPER.toArgs(factory));
        }catch (Exception e){
            return null;
        }

        return factory;
    }

    public ResourceType create(ResourceType type, long userId) {
        final RowWealth rw = new RowWealth(userId,type,
                0,
                type.equals(ResourceType.MONEY)?13000:0,
                Calendar.getInstance().getTimeInMillis());
        try {
            jdbcInsertWealths.execute(WEALTH_REVERSE_ROW_MAPPER.toArgs(rw));
        }catch (Exception e) {
            return null;
        }

        return type;
    }


    //endregion

}
