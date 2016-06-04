package com.afterqcd.study.shiro.rolebased;

import com.afterqcd.study.shiro.util.ShiroUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by afterqcd on 16/6/2.
 */
public class Application {
    public static void main(String[] args) {
        Subject zhang = ShiroUtils.login("classpath:shiro-role.ini", "zhang", "123");
        System.out.println("zhang has role1:" + zhang.hasRole("role1"));
        System.out.println("zhang has role2:" + zhang.hasRole("role2"));
        System.out.println("zhang has role3:" + zhang.hasRole("role3"));
    }
}
