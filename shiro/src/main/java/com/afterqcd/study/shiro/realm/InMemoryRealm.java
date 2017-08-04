package com.afterqcd.study.shiro.realm;

import com.google.common.collect.Lists;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Created by afterqcd on 16/6/2.
 *
 */
public class InMemoryRealm extends AuthorizingRealm {
    private final RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
    private final String salt = saltGenerator.nextBytes().toHex();

    public String getName() {
        return "in-memory-realm";
    }

    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("InMemoryRealm.doGetAuthorizationInfo" + principals);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String user = (String) principals.getPrimaryPrincipal();
        if ("zhang".equals(user)) {
            authorizationInfo.addRoles(Lists.newArrayList("role1", "role2"));
            authorizationInfo.addStringPermissions(Lists.newArrayList("user:*"));
        }
        return authorizationInfo;
    }

    /*
     * AuthenticatingRealm.doGetAuthenticationInfo只需要根据AuthenticationToken返回对应的AuthenticationInfo即可,
     * 证书比较工作有由AuthenticatingRealm.CredentialsMatcher完成.
     */
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("InMemoryRealm.doGetAuthenticationInfo" + token);

        String password = new SimpleHash(
                "SHA-256",
                token.getCredentials(),
                ByteSource.Util.bytes(token.getPrincipal() + salt),
                2
        ).toHex();

        return new SimpleAuthenticationInfo(
                token.getPrincipal(),
                password,
                ByteSource.Util.bytes(token.getPrincipal() + salt),
                getName()
        );
    }
}
