package com.inspirenetz.api.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

/**
 * User: ffl
 * Date: 18/2/14
 * Time: 5:04 PM
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.inspirenetz.api.core.auth")
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
@Order
public class SecurityTestConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    @Qualifier("uds")
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
    }

    @Bean
    public DigestAuthenticationEntryPoint digestEntryPoint ()
    {
        DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
        digestAuthenticationEntryPoint.setKey("inspirenetz");
        digestAuthenticationEntryPoint.setRealmName("user@inspirenetz.com");
        return digestAuthenticationEntryPoint;
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return userDetailsService;
    }

    public DigestAuthenticationFilter digestAuthenticationFilter (DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception {

        // Create the filter object
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();

        // IMPORTANT: Set the passwordAlreadyEncoded flag to true as we are storing the password
        // as encoded ( username:realm:password) .
        digestAuthenticationFilter.setPasswordAlreadyEncoded(true);

        // Set the entryPoint
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());

        // set the authetnicatonFilter
        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
        return digestAuthenticationFilter;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", "/app/**", "/i18n/**", "/model/**", "/view/**","/index.html","/inspirenetz-ng/app/signin.html","/upload/**","/imagedata/**","/api/0.9/json/customer/register","/api/0.9/json/customer/register/validate"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().xssProtection().disable();
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(digestEntryPoint())
                .and().authorizeRequests()
                .antMatchers("/**").authenticated()
                //.anyRequest().permitAll()
                .and().addFilter(digestAuthenticationFilter(digestEntryPoint()));




    }


}
