package com.inspirenetz.api.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by sandheepgr on 14/9/14.
 */
@Configuration
@PropertySource(value = {"classpath:integration.properties","classpath:mapping.properties"})
public class IntegrationTestConfig {

}
