package com.afterqcd.study.servlet.war.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by afterqcd on 16/6/6.
 */
public class SessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session " + se.getSession().getId() + " created");
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session " + se.getSession().getId() + " destroyed");
    }
}
