package com.afterqcd.study.servlet.war.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by afterqcd on 16/6/6.
 */
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context initialized");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context destroyed");
    }
}
