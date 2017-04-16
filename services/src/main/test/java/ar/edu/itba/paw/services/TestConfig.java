package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by juanfra on 25/03/17.
 */
@ComponentScan({ "ar.edu.itba.paw.services" })
@Configuration
public class TestConfig {

    /**
     * DataSource for the Testing of the Persistence Module. It uses a new HSQLDB dataBase
     * stored temporarily in memory
     */
    @Bean
    public UserDao mockUserDao() {
        return new MockUserDao();
    }
}