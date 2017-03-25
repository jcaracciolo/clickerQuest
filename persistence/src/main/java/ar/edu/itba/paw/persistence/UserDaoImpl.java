package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juanfra on 23/03/17.
 */
@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    private final static RowMapper<User> ROW_MAPPER = new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new User(rs.getString("username"), rs.getInt("userid"));
                }
            };

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);

        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("userid");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users ("
                        + "userid SERIAL PRIMARY KEY,"
                        + "username varchar(100)"
                        + ")");
    }

    public User findById(final long id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public User create(String username) {
        final Map<String, Object> args = new HashMap();
        args.put("username", username); // la key es el nombre de la columna
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new User(username, userId.longValue());
    }

}
