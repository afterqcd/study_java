package com.afterqcd.study.shiro.simple;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * Created by afterqcd on 16/6/2.
 */
public class Application {
    public static void main(String[] args) {
        Factory<SecurityManager> factory
                = new IniSecurityManagerFactory("classpath:shiro-simple.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123456");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            System.out.println("Succeed to login");
            subject.logout();
            System.out.println("Succeed to logout");
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
