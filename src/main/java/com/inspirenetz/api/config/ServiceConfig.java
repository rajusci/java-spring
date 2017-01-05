package com.inspirenetz.api.config;

import com.inspirenetz.api.core.loyaltyengine.CurrentProcessQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync
@EnableReactor
@ComponentScan(basePackages = {"com.inspirenetz.api.core.service.impl","com.inspirenetz.api.util","com.inspirenetz.api.core.incustomization.drools","com.inspirenetz.api.core.loyaltyengine","com.inspirenetz.api.util.integration"}, scopedProxy = ScopedProxyMode.INTERFACES)
public class ServiceConfig {

    private static final Logger log = LoggerFactory.getLogger(ServiceConfig.class);

    @Bean
    public Reactor eventReactor(Environment env) {

        // implicit Environment is injected into bean def method
        return Reactors.reactor().env(env).dispatcher(Environment.WORK_QUEUE).get();

    }


    @Bean
    public CurrentProcessQueue currentTransactionQueue() {

        return new CurrentProcessQueue();

    }

}
