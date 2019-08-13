package com.github.ruifengho.simplesecurity.autoconfigure;


import com.github.ruifengho.simplesecurity.define.PermissionExpressionParser;
import com.github.ruifengho.simplesecurity.interceptor.PermissionsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@Import({SimpleSecurityConfiguration.class})
public class SimpleSecurityWebMvcConfigurer implements WebMvcConfigurer {

    private SimpleSecurityConfiguration simpleSecurityConfiguration;

    private SimpleSecurityProperties simpleSecurityProperties;

    private PermissionExpressionParser permissionExpressionParser;


    public SimpleSecurityWebMvcConfigurer(SimpleSecurityConfiguration simpleSecurityConfiguration,SimpleSecurityProperties simpleSecurityProperties, PermissionExpressionParser permissionExpressionParser) {
        this.simpleSecurityConfiguration = simpleSecurityConfiguration;
        this.simpleSecurityProperties = simpleSecurityProperties;
        this.permissionExpressionParser = permissionExpressionParser;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionsInterceptor(simpleSecurityProperties.getExpressionList(), permissionExpressionParser));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(simpleSecurityConfiguration.signApiResolver());
    }
}
