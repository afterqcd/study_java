package com.afterqcd.study.spring.boot.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

@Component
public class MyService implements ApplicationRunner {
    // 从公共的application.properties中取配置
    @Value("${application.name}")
    private String applicationName;

    // 根据profile从application-xxx.properties中取配置
    @Value("${rest.api.server}")
    private String server;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Application name: " + this.applicationName);
        System.out.println("Rest api sever: " + this.server);
    }

    @PostConstruct
    private void checkConfiguration() {
        Assert.notNull(this.applicationName, "application.name is null");
        Assert.notNull(this.server, "rest.api.server is null");
    }
}
