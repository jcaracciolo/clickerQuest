package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Wealth;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final String IMAGE = "asda.png";
    @Autowired
    private DataSource ds;
    @Autowired
    private UserJdbcDao userDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY AND COMMIT");
        jdbcTemplate.execute("TRUNCATE TABLE factories RESTART IDENTITY AND COMMIT");
        jdbcTemplate.execute("TRUNCATE TABLE wealths RESTART IDENTITY AND COMMIT");
    }

    @Test
    public void testCreate() {
        final User user = userDao.create(USERNAME, PASSWORD,IMAGE);
        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(0,user.getId());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testId() {
        final String username2 = USERNAME + "2";
        final String username3 = USERNAME + "3";

        final User user1 = userDao.create(USERNAME, PASSWORD,IMAGE);
        final User user2 = userDao.create(username2, PASSWORD,IMAGE);
        final User user3 = userDao.create(username3, PASSWORD,IMAGE);

        assertNotNull(user1);
        assertNotNull(user2);
        assertNotNull(user3);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        assertEquals(0,user1.getId());
        assertEquals(1,user2.getId());
        assertEquals(2,user3.getId());

    }

    @Test
    public void testWealth() {
        final User user = userDao.create(USERNAME, PASSWORD,IMAGE);
        Wealth wealth = userDao.getUserWealth(user.getId());
    }
}