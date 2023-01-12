package com.webshop.IprwcBackend.security;

import com.webshop.IprwcBackend.filters.AuthenticationFilter;
import com.webshop.IprwcBackend.filters.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.http.HttpMethod.*;

@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        proxyTargetClass = true)
@Configuration
@CrossOrigin(origins = "https://iprwcfrontend2023.herokuapp.com")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService USERDETAILSSERVICE;
    private final BCryptPasswordEncoder BCRYPTPASSWORDENCODER;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(USERDETAILSSERVICE).passwordEncoder(BCRYPTPASSWORDENCODER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/login");
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.authorizeRequests().antMatchers("/api/login", "/api/users/register").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/products/**").permitAll();

        http.authorizeRequests().antMatchers(GET, "/api/token/refresh/**", "/api/users/orders", "/api/users/profile").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(PUT, "/api/users/profile").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/**", "/api/orders").hasAnyAuthority("ROLE_USER");

        http.authorizeRequests().antMatchers( "/api/**", "/api/**/**", "**").hasAnyAuthority("ROLE_ADMIN");


        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }
}
