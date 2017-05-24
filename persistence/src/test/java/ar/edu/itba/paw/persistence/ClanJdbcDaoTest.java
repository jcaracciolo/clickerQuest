package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.clan.Clan;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ClanJdbcDaoTest {
    private static final String CLAN_NAME = "AwesomeClan";
    private static User USER = new User(0,"pepe","pass","as.jpg");
    private static User USER2 = new User(0,"pepe2","pass","as.jpg");

    @Autowired
    private DataSource ds;
    @Autowired
    private ClanJdbcDao clanDao;
    @Autowired
    private UserJdbcDao userDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK");
        jdbcTemplate.execute("TRUNCATE TABLE clans RESTART IDENTITY AND COMMIT NO CHECK");
        USER = userDao.create(USER.getUsername(),USER.getPassword(),USER.getProfileImage());
        USER2 = userDao.create(USER2.getUsername(),USER2.getPassword(),USER2.getProfileImage());
    }

    @Test
    public void testCreateClan() {
        Clan clan =clanDao.createClan(CLAN_NAME);
        assertNotNull(clan);
        assertEquals(0,clan.getId());
        assertTrue(clan.getUsers().isEmpty());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "clans"));
    }

    @Test
    public void testGetClanById() {
        clanDao.createClan(CLAN_NAME);
        Clan clan = clanDao.getClanById(0);
        assertNotNull(clan);
        assertEquals(CLAN_NAME,clan.getName());
        assertTrue(clan.getUsers().isEmpty());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "clans"));
    }

    @Test
    public void testGetClanByName() {
        clanDao.createClan(CLAN_NAME);
        Clan clan = clanDao.getClanByName(CLAN_NAME);
        assertNotNull(clan);
        assertEquals(0,clan.getId());
        assertTrue(clan.getUsers().isEmpty());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "clans"));
    }

    @Test
    public void testUpdateClan() {
        Clan original = clanDao.createClan(CLAN_NAME);
        clanDao.addToClan(original.getId(),USER.getId());
        Clan clan = clanDao.getClanByName(CLAN_NAME);
        assertEquals(clan.getUsers().size(),1);
        User clanUser = clan.getUser(USER.getId());
        assertNotNull(clanUser);
        assertEquals(clanUser.getClanIdentifier().intValue(),original.getId());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "clans"));
    }

    @Test
    public void testDeleteFromClan() {
        Clan clan = clanDao.createClan(CLAN_NAME);
        clanDao.addToClan(clan.getId(),USER.getId());
        clanDao.removeFromClan(USER.getId());

        assertEquals(0,clan.getId());
        assertTrue(clan.getUsers().isEmpty());
    }

}
