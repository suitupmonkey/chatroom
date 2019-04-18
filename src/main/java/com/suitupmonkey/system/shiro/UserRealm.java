package com.suitupmonkey.system.shiro;

import com.suitupmonkey.common.utils.ClassUtil;
import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.config.ApplicationContextUtil;
import com.suitupmonkey.system.service.LoginService;
import com.suitupmonkey.system.service.impl.LoginServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;


public class UserRealm extends AuthorizingRealm {


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();//获取用户
        Set<String> permission = new HashSet<>();
        permission.add(user.getId());
        AuthorizationInfo authorization = new SimpleAuthorizationInfo(permission);
        return authorization;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        //String password = (String) token.getCredentials();
        String password = new String((char[]) token.getCredentials());

        LoginService loginService = (LoginServiceImpl)ApplicationContextUtil.getBean("loginServiceImpl", LoginServiceImpl.class);

        User user = loginService.findUser(username);

        try{
            if(ClassUtil.isEmpty(user)){
                System.out.println("user is not exist");
            }
            if(!password.equals(user.getPassword())){
                System.out.println("password is incorrect");
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }

        AuthenticationInfo authentication = new SimpleAuthenticationInfo(user,password,username);
        return authentication;
    }
}
