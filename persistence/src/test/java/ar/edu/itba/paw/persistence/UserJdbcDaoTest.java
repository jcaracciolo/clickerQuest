package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PublicUser;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import javax.sql.DataSource;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Unit test for UserDao implementation in Jdbc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    @Autowired
    private DataSource ds;
    @Autowired
    private UserJdbcDao userDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY AND COMMIT");
    }

    @Test
    public void testCreate() {
        final User user = userDao.create(USERNAME, PASSWORD);
//        assertNotNull(user);
//        assertEquals(USERNAME, user.getUsername());
//        assertEquals(PASSWORD, user.getLoginToken());
//        assertEquals(0,user.getId());
//        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

//    @Test
    public void testId() {
        final String username1 = USERNAME;
        final String username2 = USERNAME + "2";
        final String username3 = USERNAME + "3";

        final User user1 = userDao.create(username1, PASSWORD);
        final User user2 = userDao.create(username2, PASSWORD);
        final User user3 = userDao.create(username3, PASSWORD);

        assertNotNull(user1);
        assertNotNull(user2);
        assertNotNull(user3);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        assertEquals(0,user1.getId());
        assertEquals(1,user2.getId());
        assertEquals(2,user3.getId());
    }

//    @Test
    public void testUserRetrieval() {
        final String username1 = USERNAME;
        final String username2 = USERNAME + "2";

        final User user1 = userDao.create(username1, PASSWORD);
        final User user2 = userDao.create(username2, PASSWORD + "2");

        final PublicUser retrievedUser1 = userDao.findByName(username1);
        final PublicUser retrievedUser2 = userDao.findByName(username2);

        assertNotNull(user1);
        assertNotNull(user2);
        assertNotNull(retrievedUser1);
        assertNotNull(retrievedUser2);

        assertEquals(user1.getId(),retrievedUser1.getId());
        assertEquals(user1.getUsername(),retrievedUser1.getUsername());
        assertEquals(user2.getId(),retrievedUser2.getId());
        assertEquals(user2.getUsername(),retrievedUser2.getUsername());
    }
}