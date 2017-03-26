package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.PublicUser;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserJdbcDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    final static RowMapper<User> USER_CREATION_MAPPER = new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    if(!rs.getBoolean("isValid")) return  null;
                    return new User(rs.getString("usernameIN"),rs.getLong("userIdOut"), rs.getString("loginToken"));
                }
            };
    final static RowMapper<PublicUser> USER_MAPPER = new RowMapper<PublicUser>() {
                public PublicUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new PublicUser(rs.getString("userId"),rs.getLong("username"), rs.getString("publicMsg"));
                }
            };

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);

        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("userid");
    }

    PublicUser findByName(final String name) {
        final List<PublicUser> list = jdbcTemplate.query("SELECT * FROM users WHERE username = ?", USER_MAPPER, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public PublicUser findById(long id) {
        final List<PublicUser> list = jdbcTemplate.query("SELECT * FROM users WHERE userId = ?", USER_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public User validateToken(String token, long id) {
        return null;
    }

    public User login(String username, String password) {
        return null;
    }

    public User create(String username, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String saltedPassword =passwordEncoder.encode(password);
        final List<User> list = jdbcTemplate.query("SELECT * FROM createUser(?,?)", USER_CREATION_MAPPER, username,saltedPassword);

        if (list.isEmpty() || list.size() > 1) {
            throw new RuntimeException("The query should return either true or false and not more than 1 element");
        }

        return list.get(0);
    }
}
