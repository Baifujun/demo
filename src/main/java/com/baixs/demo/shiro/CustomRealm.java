package com.baixs.demo.shiro;

import com.baixs.demo.domain.User;
import com.baixs.demo.mapper.RolePermMapper;
import com.baixs.demo.mapper.UserMapper;
import com.baixs.demo.mapper.UserRoleMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermMapper rolePermissionMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进入授权逻辑");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<String> roleNames = userRoleMapper.listByUserId(user.getId());
        simpleAuthorizationInfo.addRoles(roleNames);
        List<String> permissionNames = rolePermissionMapper.listByPermissionName(user.getId());
        simpleAuthorizationInfo.addStringPermissions(permissionNames);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("进入认证逻辑");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        User user = userMapper.selectByUsername(usernamePasswordToken.getUsername());
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return simpleAuthenticationInfo;
    }
}
