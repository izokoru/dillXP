package fr.dillxp.projetdill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;/*
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;*/

@Configuration
//@EnableWebSecurity
public class CryptoConfig{
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests((authz) -> authz.anyRequest()).httpBasic(Customizer.withDefaults());
//        return http.build();
        *//*http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                .anyRequest().permitAll()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();*//*
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/register").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();

    }*/




}
