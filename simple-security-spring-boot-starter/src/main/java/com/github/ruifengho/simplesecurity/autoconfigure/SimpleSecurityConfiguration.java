package com.github.ruifengho.simplesecurity.autoconfigure;

import com.github.ruifengho.simplesecurity.define.PermissionExpressionParser;
import com.github.ruifengho.simplesecurity.jwt.JwtTokenParser;
import com.github.ruifengho.simplesecurity.jwt.support.DefaultJwtTokenParser;
import com.github.ruifengho.simplesecurity.annotation.support.PreAuthorizeAspect;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SimpleSecurityProperties.class)
@AutoConfigureBefore(SimpleSecurityWebMvcConfigurer.class)
public class SimpleSecurityConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public JwtTokenParser jwtTokenParser(SimpleSecurityProperties simpleSecurityProperties) {
        return new DefaultJwtTokenParser(simpleSecurityProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public PermissionExpressionParser permissionExpressionParser(JwtTokenParser jwtTokenParser) {
        return new PermissionExpressionParser(jwtTokenParser);
    }

    @Bean
    @ConditionalOnMissingBean
    public PreAuthorizeAspect preAuthorizeAspect(PermissionExpressionParser permissionExpressionParser) {
        return new PreAuthorizeAspect(permissionExpressionParser);
    }


}