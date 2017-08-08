package com.afterqcd.study.spring.boot.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @RequestMapping(path = "/api/auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void auth(@RequestBody UsernamePasswordToken token) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered() || subject.isAuthenticated()) {
            subject.logout();
        }
        subject.login(token);
    }
}
