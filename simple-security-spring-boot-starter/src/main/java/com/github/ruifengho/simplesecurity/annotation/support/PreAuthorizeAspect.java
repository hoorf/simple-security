package com.github.ruifengho.simplesecurity.annotation.support;


import com.github.ruifengho.simplesecurity.annotation.PreAuthorize;
import com.github.ruifengho.simplesecurity.define.PermissionExpressionParser;
import com.github.ruifengho.simplesecurity.exception.SimpleSecurityException;
import com.github.ruifengho.simplesecurity.util.SpringElCheckUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

@Aspect
public class PreAuthorizeAspect {

    private final PermissionExpressionParser permissionExpressionParser;

    public PreAuthorizeAspect(PermissionExpressionParser permissionExpressionParser) {
        this.permissionExpressionParser = permissionExpressionParser;
    }

    @Around("@annotation(com.github.ruifengho.simplesecurity.annotation.PreAuthorize) ")
    public Object preAuth(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(PreAuthorize.class)) {
            PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);

            String expression = preAuthorize.value();
            boolean check = SpringElCheckUtil.check(new StandardEvaluationContext(permissionExpressionParser), expression);

            if (!check) {
                throw new SimpleSecurityException("Access Denied.");
            }
        }
        return point.proceed();
    }
}
