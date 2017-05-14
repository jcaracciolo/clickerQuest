package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

/**
 * Created by juanfra on 22/03/17.
 */

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.webapp.controller, ar.edu.itba.paw.services, ar.edu.itba.paw.persistence" })
@Configuration
@Import(value = { WebAuthConfig.class })
public class WebConfig {

    // --------- WEBAPP
    /**
     * ViewResolver for the Webapp. JSP files located in WEB-INF/jsp/ will composed
     * the different views of our Webapp
     */
    @Bean
    public ViewResolver viewResolver() {

        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;

    }

    // ---------- PERSISTENCE

    /**
     * DataSource which indicates how the application should access the database. Sets up
     * the port, address, database name, user and password for it, as well as the driver.
     */
    @Bean
    @Profile("default")
    public DataSource devDataSource() {

        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl("jdbc:postgresql://localhost/clickerQuest");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;

    }

    /**
     * DataSource which indicates how the application should access the database. Sets up
     * the port, address, database name, user and password for it, as well as the driver.
     */
    @Bean
    @Profile("production")
    public DataSource productionDataSource() {

        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl("jdbc:postgresql://10.16.1.110/paw-2017a-4");
        ds.setUsername("paw-2017a-4");
        ds.setPassword("ooc4Choo");
        return ds;

    }

    /**
     * Initializer for the database, creates a new DataSource in which {@see DatabasePopulator}
     * will be in charge of populating it at the beginning of the process
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
        final DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        dsi.setDatabasePopulator(databasePopulator());
        return dsi;
    }

    /**
     * Schema to be executed at the start of the WebApp to setup the project's database
     */
    @Value("classpath:schema.sql")
    private Resource schemaSql;
    /**
     * DatabasePopulator that loads the corresponding script, in charge of populating
     * the database.
     */
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScript(schemaSql);
        return dbp;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return messageSource;
    }

}
