package realm;

import dao.UserDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import po.User;

import javax.annotation.Resource;
import java.util.*;

public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获得认证数据中的用户名
        String username = (String) principals.getPrimaryPrincipal();
        Set<String> permissions = new HashSet<>();
        Set<String> roles = getUserRolesByName(username);
        for(String s:roles){
            Set<String> tem = getUserPermissionByName(s);
            permissions.addAll(tem);
        }



        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }

    private Set<String> getUserPermissionByName(String username) {
        List<String> list = userDao.getPermissionByName(username);
        Set<String> permission = new HashSet<String>(list);
        return  permission;
    }

    private Set<String> getUserRolesByName(String username) {
        List<String> list = userDao.getRolesByName(username);
        Set<String> roles = new HashSet<String>(list);
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
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,passwd,"custom");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return authenticationInfo;
    }

    private String getUserPasswdByName(String name) {

        User user = userDao.getUserByName(name);
        if(user != null){
            return user.getPassward();
        }
        return null;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("what","what");
        System.out.println(md5Hash.toString());
    }
}
