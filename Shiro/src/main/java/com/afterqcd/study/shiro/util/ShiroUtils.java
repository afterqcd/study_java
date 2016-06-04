package com.afterqcd.study.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * Created by afterqcd on 16/6/2.
 */
public class ShiroUtils {
    /**
     * 用指定的user和password登录.
     * @param iniFile ini配置文件
     * @param user 用户名
     * @param password 密码
     * @return 已登录的subject
     */
    public static Subject login(String iniFile, String user, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniFile);
        SecurityUtils.setSecurityManager(factory.getInstance());

        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(user, password));
        return subject;
    }
}
