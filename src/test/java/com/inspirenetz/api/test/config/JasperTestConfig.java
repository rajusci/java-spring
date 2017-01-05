package com.inspirenetz.api.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ameen on 28/8/15.
 */
@Configuration
@PropertySource(value = "classpath:jasperconfig-test.properties")
public class JasperTestConfig {
}
