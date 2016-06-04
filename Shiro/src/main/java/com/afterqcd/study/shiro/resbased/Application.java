package com.afterqcd.study.shiro.resbased;

import com.afterqcd.study.shiro.util.ShiroUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by afterqcd on 16/6/2.
 */
public class Application {
    public static void main(String[] args) {
        Subject zhang = ShiroUtils.login("classpath:shiro-res.ini", "zhang", "123");
        System.out.println("zhang has user:create permission:" + zhang.isPermitted("user:create"));
        System.out.println("zhang has user:update permission:" + zhang.isPermitted("user:update"));
        System.out.println("zhang has user:delete permission:" + zhang.isPermitted("user:delete"));
    }
}
