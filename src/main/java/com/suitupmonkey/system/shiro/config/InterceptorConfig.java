package com.suitupmonkey.system.shiro.config;

import com.suitupmonkey.system.shiro.filter.AccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(new AccessInterceptor());
        addInterceptor.excludePathPatterns("/css/**");//不拦截静态资源
        addInterceptor.excludePathPatterns("/js/**");//不拦截静态资源
        addInterceptor.excludePathPatterns("/bootstrap/**");//不拦截静态资源
        addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/login");
        //addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/loginCheck");
        addInterceptor.addPathPatterns("/**");
    }
}
