package com.afterqcd.study.spring.property;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by afterqcd on 16/6/8.
 */
@Configuration
@PropertySource("classpath:property/application.properties")
@ComponentScan("com.afterqcd.study.spring.property")
public class ApplicationConfig {
    /*
     * 在context中注册PropertySourcesPlaceholderConfigurer bean,
     * 才能够解析@Value中的"${}"
     */
    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
