package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.security.Principal;
import java.util.Properties;

@ComponentScan({ "ar.edu.itba.paw.persistence","ar.edu.itba.paw.webapp" })
@Configuration
public class TestConfig {

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory (DataSource datasource) {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan( "ar.edu.itba.paw.model" );
        factoryBean.setDataSource(datasource);
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        final Properties properties = new Properties();
        properties.setProperty( "hibernate.hbm2ddl.auto" , "update" );
        properties.setProperty( "hibernate.dialect" , "org.hibernate.dialect.HSQLDialect" );
        // Si ponen esto en prod, hay tabla!!!
        properties.setProperty( "hibernate.show_sql" , "true" );
        properties.setProperty( "format_sql" , "true" );
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
        ds.setPassword("");
        return ds;
    }
}