package com.inspirenetz.api.test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Configuration
@PropertySource(value = "classpath:inconfig.properties")
public class ApplicationTestConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationTestConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties()
    {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]{new ClassPathResource("app-test.properties"), new ClassPathResource("dao-test.properties")};
        configurer.setLocations(resources);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        log.info("app.properties loaded.");
        return configurer;
    }

    @Bean
    public Mapper mapper()
    {
        DozerBeanMapper mapper = new DozerBeanMapper();
        List<String> dozerMappingFiles = new ArrayList<String>();
        dozerMappingFiles.add("dozer.xml");
        mapper.setMappingFiles(dozerMappingFiles);
        return mapper;
    }



    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }


    @Bean
    public CommonsMultipartResolver multipartResolver() {

        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(50000000);
        return resolver;

    }
}
