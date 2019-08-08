package com.github.ruifengho.simplesecurity.autoconfigure;


import com.github.ruifengho.simplesecurity.annotation.support.EncryptRequestBodyAdvice;
import com.github.ruifengho.simplesecurity.annotation.support.EncryptResponseBodyAdvice;
import com.github.ruifengho.simplesecurity.define.PermissionExpressionParser;
import com.github.ruifengho.simplesecurity.interceptor.PermissionsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Import({SimpleSecurityConfiguration.class})
public class SimpleSecurityWebMvcConfigurer implements WebMvcConfigurer {

    private SimpleSecurityProperties simpleSecurityProperties;

    private PermissionExpressionParser permissionExpressionParser;


    public SimpleSecurityWebMvcConfigurer(SimpleSecurityProperties simpleSecurityProperties, PermissionExpressionParser permissionExpressionParser) {
        this.simpleSecurityProperties = simpleSecurityProperties;
        this.permissionExpressionParser = permissionExpressionParser;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionsInterceptor(simpleSecurityProperties.getExpressionList(), permissionExpressionParser));
    }
}
