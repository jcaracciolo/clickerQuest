package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FactoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class FactoryServiceImplTest {

    @Autowired
    FactoryService factoryService;

    //TODO add more test when logic expands
    @Test
    public void test(){
        assertTrue(true);
    }

}
