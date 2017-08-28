package com.afterqcd.study.spring.boot.shiro;

import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {
    @Bean
    SessionManager sessionManager() {
        return new DefaultWebSessionManager();
    }

    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/api/auth", "anon");
        chainDefinition.addPathDefinition("/api/**", "authc");
        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
    }
}
