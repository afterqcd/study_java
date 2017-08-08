package com.afterqcd.study.shiro.realm;

import com.afterqcd.study.shiro.util.ShiroUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by afterqcd on 16/6/2.
 */
public class Application {
    public static void main(String[] args) {
        Subject zhang = ShiroUtils.login("classpath:shiro-realm.ini", "zhang", "123");
        System.out.println("zhang has role1 " + zhang.hasRole("role1"));
        System.out.println("zhang has role3 " + zhang.hasRole("role3"));
        System.out.println("zhang has permission user:create " + zhang.isPermitted("user:create"));
        System.out.println("zhang has permission user:delete " + zhang.isPermitted("user:delete"));
        System.out.println("zhang has permission user:update " + zhang.isPermitted("user:update"));
    }
}
