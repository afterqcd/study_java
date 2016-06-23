package com.afterqcd.study.spring.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

/**
 * Created by afterqcd on 16/6/22.
 */
@Configuration
@ComponentScan(basePackages = "com.afterqcd.study.spring.mvc",
        excludeFilters = { @ComponentScan.Filter(Controller.class) })
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
