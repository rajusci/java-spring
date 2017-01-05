package com.inspirenetz.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Configuration
@ComponentScan(basePackages = {"com.inspirenetz.api.rest"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{

    private final Logger log = LoggerFactory.getLogger(WebConfig.class);


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(resolver());
    }


    @Bean
    public ServletWebArgumentResolverAdapter resolver() {
        return new ServletWebArgumentResolverAdapter(pageable());
    }

    @Bean
    public PageableArgumentResolver pageable() {
        return new PageableArgumentResolver();
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CORSInterceptor());
    }


    /*
     * Enable this if you want to have the imagedata folder under the inspirenetz-api
     * THIS MAY CLEAR THE FOLDER WHEN A NEW WAR FILE IS DEPLOYED.
     * HENCE WE HAVE KEPT THE IMAGES IN A DIFFERENT FOLDER UNDER THE WEBSERVER
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /**
         * IMPORTANT NOTE: it is necessary to have the /imagedata/ path added as resource and
         *                 point to the correct location here.
         *                 Otherwise spring will try to take over the path and give 404 message


        registry.addResourceHandler("/imagedata/**").addResourceLocations("/imagedata/");
    }

    */
}
