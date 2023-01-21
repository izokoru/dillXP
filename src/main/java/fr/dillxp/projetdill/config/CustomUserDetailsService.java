package fr.dillxp.projetdill.config;

import org.springframework.context.annotation.Bean;/*
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;*/
import org.springframework.jdbc.datasource.DriverManagerDataSource;/*
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
*/
import javax.sql.DataSource;
import java.sql.DriverManager;

public class CustomUserDetailsService /*implements UserDetailsService*/ {


    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("dillxp");
        return dataSource;
    }

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/


/*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return null;
    }*/
}
