package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by Julian Benitez on 6/14/2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserHibernateDaoTest {
    @Autowired
    UserHibernateDao userHibernateDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource ds;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void test(){
        Collection<User> createdUsers = new ArrayList<>();
        User created = userHibernateDao.create("cripto","cripto","im1");
        User retrieved =userHibernateDao.findById(1);
        assertEquals(created,retrieved);
        createdUsers.add(created);
        created = userHibernateDao.create("cripto2","cripto","im1");
        retrieved =userHibernateDao.findById(2);
        assertEquals(created,retrieved);
        createdUsers.add(created);
        retrieved =userHibernateDao.findByUsername("cripto2");
        assertEquals(created,retrieved);
        Collection<User> retrievedUsers = userHibernateDao.findByKeyword("Cri");
        assertEquals(createdUsers,retrievedUsers);
    }
}
