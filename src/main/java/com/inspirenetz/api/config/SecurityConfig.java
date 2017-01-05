package com.inspirenetz.api.config;

import com.inspirenetz.api.core.auth.CustomAccessDeniedHandler;
import com.inspirenetz.api.core.auth.IPBasedAuthenticationEntryPoint;
import com.inspirenetz.api.core.auth.IPBasedAuthenticationFilter;
import com.inspirenetz.api.core.auth.RestDigestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import javax.validation.constraints.Past;

/**
 * User: ffl
 * Date: 18/2/14
 * Time: 5:04 PM
 */
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.inspirenetz.api.core.auth")
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true,proxyTargetClass = true)
@Order
@PropertySource(value = "classpath:ipfilters.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("uds")
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment environment;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return userDetailsService;
    }



    @Bean
    public DigestAuthenticationEntryPoint digestEntryPoint ()
    {
        RestDigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new RestDigestAuthenticationEntryPoint();
        //DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
        digestAuthenticationEntryPoint.setKey("inspirenetz");
        digestAuthenticationEntryPoint.setRealmName("user@inspirenetz.com");
        return digestAuthenticationEntryPoint;
    }



    @Bean
    public IPBasedAuthenticationEntryPoint ipBasedEntryPoint() {

        // Create the Entry Point
        IPBasedAuthenticationEntryPoint ipBasedAuthenticationEntryPoint = new IPBasedAuthenticationEntryPoint();

        // Set the realName
        ipBasedAuthenticationEntryPoint.setRealmName("user@inspirenetz.com");

        // Return the entry point
        return ipBasedAuthenticationEntryPoint;

    }

    /**
     * Function to get the DigestAuthenticationFilter object
     * Here the function set the password as already encoded
     *
     * @param digestAuthenticationEntryPoint        - The DigestAuthenticationEntryPoint object passed
     * @return
     *
     * @throws Exception
     */
    public DigestAuthenticationFilter digestAuthenticationFilter (DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception {

        // Create the filter object
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();

        // IMPORTANT: Set the passwordAlreadyEncoded flag to true as we are storing the password
        // as encoded ( username:realm:password) .
        digestAuthenticationFilter.setPasswordAlreadyEncoded(true);

        // Set the entryPoint
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());

        // set the authenticationFilter
        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());



        // Return the filter
        return digestAuthenticationFilter;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", "/app/**", "/i18n/**", "/model/**", "/view/**","/index.html","/inspirenetz-ng/app/signin.html","/upload/**","/imagedata/**","/api/0.9/json/customer/register","/api/0.9/json/customer/register/validate","/api/0.9/json/customer/forgetpassword/generateotp","/api/0.9/json/customer/forgetpassword","/api/0.9/json/user/registrationstatus","/api/0.9/json/customer/register/compatible","/api/0.9/json/merchant/codedvalues/map/{cdvIndex}","/api/0.9/json/public/catalogues/**","/api/0.9/json/public/promotions","/api/0.9/json/public/merchants","/api/0.9/json/user/register","/api/0.9/json/user/register/validate","/api/0.9/json/user/register/generateotp","/api/0.9/json/user/forgotpassword/generateotp","/api/0.9/json/user/forgotpassword/validate","/api/0.9/json/user/catalogue/preference/{merchantNo}","/api/0.9/json/customer/merchant/profile/{merUrlName}","/api/0.9/json/user/register/compatible","/api/0.9/json/user/register/validate","/api/0.9/json/public/cardnumber/validate","/api/0.9/json/public/card/issue","/api/0.9/json/public/card/getbalanceotp","/api/0.9/json/public/card/getbalance"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.headers().xssProtection().disable();
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic()
                .authenticationEntryPoint(ipBasedEntryPoint())
                .and().authorizeRequests()
                .antMatchers("/local/**").authenticated()
                .and().addFilter(ipBasedAuthenticationFilter(ipBasedEntryPoint()));

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(digestEntryPoint())
                .and().authorizeRequests()
                .antMatchers("/api/**").authenticated()
                //.anyRequest().permitAll()
                .and().addFilter(digestAuthenticationFilter(digestEntryPoint()));



    }

    public IPBasedAuthenticationFilter ipBasedAuthenticationFilter(IPBasedAuthenticationEntryPoint ipBasedAuthenticationEntryPoint) {

        // Create the IPBasedAuthenticationFilter
        IPBasedAuthenticationFilter ipBasedAuthenticationFilter = new IPBasedAuthenticationFilter();

        // Set the authentication entry point
        ipBasedAuthenticationFilter.setAuthenticationEntryPoint(ipBasedAuthenticationEntryPoint);

        // SEt the UserDetailsService
        ipBasedAuthenticationFilter.setUserDetailsService(userDetailsService());

        // Set the environment
        ipBasedAuthenticationFilter.setEnvironment(environment);

        // Return the object
        return ipBasedAuthenticationFilter;

    }

}
