package org.github.ruifengho.simplesecurity.interceptor;

import org.github.ruifengho.simplesecurity.define.PermissionExpression;
import org.github.ruifengho.simplesecurity.define.PermissionExpressionParser;
import org.github.ruifengho.simplesecurity.exception.SimpleSecurityException;
import org.github.ruifengho.simplesecurity.util.AntPathMatcherUtil;
import org.github.ruifengho.simplesecurity.util.SpringElCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 拦截器
 */
public class PermissionsInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(PermissionsInterceptor.class);

    private List<PermissionExpression> expressions;

    private PermissionExpressionParser permissionExpressionParser;

    public PermissionsInterceptor(List<PermissionExpression> expressions, PermissionExpressionParser permissionExpressionParser) {
        this.expressions = expressions;
        this.permissionExpressionParser = permissionExpressionParser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.error("{}", expressions);

        boolean check = expressions.stream().filter(exp -> AntPathMatcherUtil.match(request, exp.getHttpMethod(), exp.getPath())).findFirst().map(exp -> SpringElCheckUtil.check(new StandardEvaluationContext(permissionExpressionParser), exp.getExpression())).orElse(true);
        if (!check) {
            throw new SimpleSecurityException("Access Denied.");
        }
        return true;
    }
}
