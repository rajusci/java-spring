package com.inspirenetz.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.license.LicenseValidator;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.inspirenetz.api.config")
@PropertySource(value = "classpath:inconfig.properties")
public class ApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties()
    {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]{new ClassPathResource("app.properties"), new ClassPathResource("dao.properties")};
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
    public ObjectMapper objectMapper() {

        return new ObjectMapper();

    }


    @Bean
    public CommonsMultipartResolver multipartResolver() {

        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(50000000);
        return resolver;

    }

    @Bean
    public LicenseValidator getLicense() {

        return new LicenseValidator();

    }

    /*
    @Bean
    public TaskExecutor taskExecutor() {

        return new ThreadPoolTaskExecutor();

    }
    */
}
