package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.packages.Implementations.Productions;
import ar.edu.itba.paw.model.packages.Implementations.Storage;
import ar.edu.itba.paw.model.packages.PackageBuilder;
import ar.edu.itba.paw.model.packages.Paginating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.*;

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

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsertUsers;
    private final SimpleJdbcInsert jdbcInsertFactories;
    private final SimpleJdbcInsert jdbcInsertWealths;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserJdbcDao.class);

    //region ROWMAPPER
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

    final static RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
    {
        Integer clanid = rs.getInt("clanid");
        if(rs.wasNull()) clanid = null;
        return new User(rs.getLong("userid"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("profileImage"),
                rs.getDouble("score"),
                clanid
                );
    };



    final static ReverseRowMapper<User> USER_REVERSE_ROW_MAPPER = (us) ->
    {
        final Map<String, Object> args = new HashMap();
        args.put("username",        us.getUsername());
        args.put("password",        us.getPassword());
        args.put("profileImage",    us.getProfileImage());
        args.put("score",           us.getScore());
        args.put("clanId",          us.getClanIdentifier());
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
    @Override
    public User create(String username, String password,String img) {
        final User us = new User(0,username,password,img);
        final long userId;

        try {
             userId = jdbcInsertUsers.executeAndReturnKey(USER_REVERSE_ROW_MAPPER.toArgs(us)).longValue();
        }catch (Exception e) {
            return null;
        }

        return new User(userId,username,password,img);
    }

    @Override
    public Wealth create(Wealth wealth) {
        Storage s = wealth.getStorage();
        Productions p = wealth.getProductions();
        wealth.getProductions().getResources().forEach( (r) -> {
                    RowWealth rw = new RowWealth(wealth.getUserid(),
                            r,
                            p.getValue(r),
                            s.getValue(r),
                            s.getLastUpdated(r).getTimeInMillis()
                    );
                    jdbcInsertWealths.execute(WEALTH_REVERSE_ROW_MAPPER.toArgs(rw));
                }
        );

        return wealth;
    }

    @Override
    public User update(User u) {
        int rows = jdbcTemplate.update(
                "UPDATE users SET " +
                        "username = ?," +
                        "password = ?," +
                        "profileImage = ?," +
                        "score = ?, " +
                        "clanid = ? " +
                        " WHERE (userid = ?) ;",
                u.getUsername(),
                u.getPassword(),
                u.getProfileImage(),
                u.getScore(),
                u.getClanIdentifier(),
                u.getId());
        if (rows == 1) {
            return u;
        } else if (rows > 1) {
            //TODO multiple  updates

            return null;
        } else {
            LOGGER.info("User has not been updated. The number of rows was < 1");
            return null;
        }
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
           LOGGER.info("Factory has not been updated. The number of rows equals to 0");
           return null;
        } else {
            //TODO multiple updates
            return null;
        }
    }

    public Wealth update(Wealth w) {
        Storage s = w.getStorage();
        Productions p = w.getProductions();
        for(ResourceType r: w.getProductions().getResources()) {
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
                LOGGER.info("Wealth has not been updated. The number of rows equals to 0");
                return w;
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

    public Collection<User> findByKeyword(final String search){
        StringBuilder s = new StringBuilder(search.toLowerCase()).append("%").insert(0,"%");
        return jdbcTemplate.query("SELECT * FROM users where lower(username) like lower(?)", USER_ROW_MAPPER, s.toString());
    }

    @Override
    public User findByUsername(String username) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE username = ?", USER_ROW_MAPPER, username);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
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
        if(list.isEmpty()) {
            return null;
        }

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

    public Factory create(Factory factory){
        try {
            jdbcInsertFactories.execute(FACTORY_REVERSE_ROW_MAPPER.toArgs(factory));
        }catch (Exception e){
            return null;
        }

        return factory;
    }

    //endregion


    @Override
    public Integer getGlobalRanking(long userId) {
        List<Integer> values =  jdbcTemplate.query("SELECT row_number FROM " +
                                    "(SELECT ROW_NUMBER() OVER(ORDER BY score DESC),* FROM users) as u " +
                                    "WHERE userid = ?;",
                                    (rs, rowNum) -> rs.getInt("row_number"),
                                    userId);

        if (values.size() == 0) {
            LOGGER.info("When querying for the Global Ranking, it returns no results.");
            return null;
        } else if(values.size() >1) {
            //TODO multiple updates
            return null;
        }

        return values.get(0);
    }

    @Override
    public Paginating<User> globalUsers(int page, int userPerPage) {

        if(page<=0 || userPerPage<=0) {
            throw new IllegalArgumentException("Page and maxPage must be an positive integer");
        }

        int min = (page -1) * userPerPage + 1;
        int max = page * userPerPage;

        List<User> users = jdbcTemplate.query(
                "SELECT * FROM ( SELECT ROW_NUMBER() OVER(ORDER BY score DESC),* FROM users) as u" +
                        " WHERE row_number BETWEEN ? AND ?",
                USER_ROW_MAPPER,
                min,max);

        List<Integer> amount = jdbcTemplate.query("SELECT COUNT(*) FROM USERS",(rs, rowNum) -> rs.getInt("count"));

        if(users.isEmpty()) {
            return null;
        } else {
            int totalUsers = amount.get(0);
            int totalPages = (int)Math.ceil(totalUsers/((double)userPerPage));
            return new Paginating<>(page,userPerPage,amount.get(0),totalPages,users);
        }
    }
}
