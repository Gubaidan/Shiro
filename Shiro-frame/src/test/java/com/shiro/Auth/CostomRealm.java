package com.shiro.Auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CostomRealm extends AuthorizingRealm {
    Map<String,String> usermap = new HashMap<String, String>();
    {
        usermap.put("sa","123");
        super.setName("custom");
    }
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获得认证数据中的用户名
        String username = (String) principals.getPrimaryPrincipal();
        Set<String> roles = getUserRolesByName(username);

        Set<String> permissions = getUserPermissionByName(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }

    private Set<String> getUserPermissionByName(String username) {
        Set<String> permission = new HashSet<String>();
        permission.add("user:add");
        permission.add("user:delete");
        return  permission;
    }

    private Set<String> getUserRolesByName(String username) {
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获得认证数据中的用户名
        String username = (String) token.getPrincipal();

        // 通过用户名获取数据库密码
        String passwd = getUserPasswdByName(username);
        if(passwd == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("sa",passwd,"custom");
        return authenticationInfo;
    }

    private String getUserPasswdByName(String name) {
        return usermap.get(name);
    }
}
