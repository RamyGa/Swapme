package com.ramy.swapme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//Custom security configuration class

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception { //authorize incoming requests
        http
           .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                .antMatchers("/").permitAll()//if there is nothing after the domain then /, no auth required
                .antMatchers("/test").hasRole("ADMIN")
                .antMatchers("/up_for_swap").hasRole("USER")//user needs to log in.
                .antMatchers("/my_card_wallet").hasRole("USER")//user needs to log in.
                .antMatchers("/choose_card").hasRole("USER")//user needs to log in.
                .antMatchers("/add_card").hasRole("USER")//user needs to log in.



                .and() //"ADMIN" Role is assigned in-> application.properties
           .formLogin()//we want to allow the user to have a form login for this restricted /submit url
                .defaultSuccessUrl("/up_for_swap", true)//overrides the default "/" after login success
            .and()
            .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .usernameParameter("email")
                .and()
                .logout()
                .and()
                .rememberMe();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { //data source
        auth.userDetailsService(userDetailsService); // will provide the returned email and password of the authenticated user

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
