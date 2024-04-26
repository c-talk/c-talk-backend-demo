package me.a632079.ctalk.config;

import me.a632079.ctalk.interceptor.CorsInterceptor;
import me.a632079.ctalk.interceptor.UserInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @className: MvcInterceptorConfig
 * @description: MvcInterceptorConfig - 拦截器注册
 * @version: v1.0.0
 * @author: haoduor
 */

@Configuration
public class MvcInterceptorConfig extends WebMvcConfigurationSupport {

    @Resource
    private UserInfoInterceptor userInfoInterceptor;

    @Resource
    private CorsInterceptor corsInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        registry.addInterceptor(corsInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(userInfoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/ping");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(false);
    }
}
