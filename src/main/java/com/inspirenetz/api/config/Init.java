package com.inspirenetz.api.config;



import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

/**
 * User: ffl
 * Date: 18/2/14
 * Time: 1:56 PM
 */
public class Init extends AbstractAnnotationConfigDispatcherServletInitializer{
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ ApplicationConfig.class, SecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{  WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }

    @Override
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", SimpleCORSFilter.class);
        corsFilter.addMappingForUrlPatterns(null, false, "/*");
        return super.registerServletFilter(servletContext, filter);
    }
}
