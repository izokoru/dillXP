package fr.dillxp.projetdill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    public DataSource dataSource;


    /*@Bean
    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {

        UserDetails user = User
                .withUsername("user")
                .password("password")
                .roles("USER_ROLE")
                .build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        return users;
    }*/

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(jdbcUserDetailsManager()).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests((authz) -> authz.anyRequest()).httpBasic(Customizer.withDefaults());
//        return http.build();
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/connexion").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/test").permitAll()
                //.requestMatchers("/api/utilisateur/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/utilisateur/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/utilisateur/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/utilisateur/**").authenticated()

                //.requestMatchers(HttpMethod.POST, "/api/utilisateur/{username}/ajouterProduit**").authenticated()

                //.requestMatchers("/employee/**").hasRole("USER")
                //.requestMatchers("/manager/**").hasRole("MANAGER")
                //.anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();

    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager()
    {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);


        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
