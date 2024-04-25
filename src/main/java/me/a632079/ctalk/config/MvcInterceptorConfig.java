package me.a632079.ctalk.config;

import me.a632079.ctalk.interceptor.UserInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @className: MvcInterceptorConfig
 * @description: MvcInterceptorConfig - 拦截器注册
 * @version: v1.0.0
 * @author: haoduor
 */

@Configuration
public class MvcInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private UserInfoInterceptor userInfoInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        registry.addInterceptor(userInfoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/ping");
    }
}
