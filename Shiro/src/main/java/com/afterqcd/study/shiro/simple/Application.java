package com.afterqcd.study.shiro.simple;

import com.afterqcd.study.shiro.util.ShiroUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;

/**
 * Created by afterqcd on 16/6/2.
 */
public class Application {
    public static void main(String[] args) {
        try {
            Subject subject = ShiroUtils.login("classpath:shiro-simple.ini", "zhang", "123");
            System.out.println("Succeed to login");
            subject.logout();
            System.out.println("Succeed to logout");
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
