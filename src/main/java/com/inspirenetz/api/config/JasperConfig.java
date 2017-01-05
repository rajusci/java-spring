package com.inspirenetz.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ameen on 28/8/15.
 */
@Configuration
@PropertySource(value = "classpath:jasperconfig.properties")
public class JasperConfig {
}
