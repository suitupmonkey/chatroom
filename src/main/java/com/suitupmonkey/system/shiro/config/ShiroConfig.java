package com.suitupmonkey.system.shiro.config;

import com.suitupmonkey.system.shiro.UserRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

//shiro配置类
@Configuration
public class ShiroConfig implements Serializable {


    @Bean
    public Realm realm(){
        UserRealm realm = new UserRealm();
        return realm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //Single realm app.  If you have multiple realms, use the 'realms' property instead.
        securityManager.setRealm(realm());//设置realm
        return securityManager;
    }

    //不知道干啥的，官网上给的东西。
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){

        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(){
        MethodInvokingFactoryBean methodInvokingFactoryBean
                = new MethodInvokingFactoryBean();
        //?
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(securityManager());
        return methodInvokingFactoryBean;
    }





}
