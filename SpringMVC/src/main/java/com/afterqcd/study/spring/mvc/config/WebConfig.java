package com.afterqcd.study.spring.mvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;

/**
 * Created by afterqcd on 16/6/22.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.afterqcd.study.spring.mvc")
public class WebConfig extends WebMvcConfigurerAdapter implements ServletContextAware {

    private String rootPath;

    public void setServletContext(ServletContext servletContext) {
        this.rootPath = servletContext.getRealPath("/");
    }

    @Bean
    FreeMarkerConfigurer freeMarkerConfigurer(
            @Value("${free.marker.template.root}") String templateRoot
    ) throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration();
        configuration.setDirectoryForTemplateLoading(new File(rootPath + templateRoot));
        configuration.setDefaultEncoding("UTF-8");

        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setConfiguration(configuration);
        return configurer;
    }

    @Bean
    FreeMarkerViewResolver freeMarkerViewResolver(
            @Value("${free.marker.suffix}") String suffix
    ) {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setSuffix(suffix);
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
