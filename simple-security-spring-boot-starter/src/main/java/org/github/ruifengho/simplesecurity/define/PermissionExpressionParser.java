package org.github.ruifengho.simplesecurity.define;

import org.github.ruifengho.simplesecurity.jwt.JwtTokenParser;
import org.github.ruifengho.simplesecurity.jwt.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 处理权限表达式
 */
public class PermissionExpressionParser {

    private static final Logger logger = LoggerFactory.getLogger(PermissionExpressionParser.class);

    private JwtTokenParser jwtTokenParser;


    public PermissionExpressionParser(JwtTokenParser jwtTokenParser) {
        this.jwtTokenParser = jwtTokenParser;
    }


    /**
     * 匿名即可访问
     *
     * @return true
     */
    public boolean anon() {
        return true;
    }

    /**
     * 登录才能访问
     *
     * @return 如已登录，则返回true
     */
    public boolean hasLogin() {
        return jwtTokenParser.getUser() != null;
    }

    /**
     * 拥有指定角色才能访问
     *
     * @param role 角色
     * @return 如果拥有指定角色，则返回true
     */
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    /**
     * 拥有所有指定角色才能访问
     *
     * @param roles 角色
     * @return 如果拥有roles所有角色，则返回true
     */
    public boolean hasAllRoles(String... roles) {
        JwtUser user = jwtTokenParser.getUser();
        if (user == null) {
            return false;
        }

        List<String> userRoles = user.getPermissions();
        if (CollectionUtils.isEmpty(userRoles)) {
            return false;
        }
        List<String> roleList = Arrays.asList(roles);
        return userRoles.containsAll(roleList);
    }

    /**
     * 拥有指定角色之一即可访问
     *
     * @param roles 角色
     * @return 如果拥有roles元素之一，则返回true
     */
    public boolean hasAnyRoles(String... roles) {
        JwtUser user = jwtTokenParser.getUser();
        if (user == null) {
            return false;
        }

        List<String> userRoles = user.getPermissions();
        List<String> roleList = Arrays.asList(roles);
        if (CollectionUtils.isEmpty(userRoles)) {
            return false;
        }

        boolean checkResult = userRoles.stream()
                .anyMatch(roleList::contains);
        if (!checkResult) {
            logger.warn("权限不匹配，userRolesFromToken = {}, roles = {}", userRoles, roles);
        }
        return checkResult;
    }

}
