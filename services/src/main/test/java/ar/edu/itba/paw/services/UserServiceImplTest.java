package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    //TODO add more test when logic expands
    @Test
    public void test(){
        assertTrue(true);
    }

    @Test
    public void testCreateUser(){
        String username = "Daniel";
        String password = "l0b0--";
        String img = "1.img";
        User user = userService.create(username,password,img);

        assertNotNull(user);
        assertEquals(user.getUsername(),username);
        assertEquals(user.getPassword(),password);
        assertEquals(user.getProfileImage(),img);
    }


}
