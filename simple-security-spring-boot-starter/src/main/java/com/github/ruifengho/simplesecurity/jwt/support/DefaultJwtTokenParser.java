package com.github.ruifengho.simplesecurity.jwt.support;

import io.jsonwebtoken.Claims;
import com.github.ruifengho.simplesecurity.autoconfigure.SimpleSecurityProperties;
import com.github.ruifengho.simplesecurity.exception.SimpleSecurityException;
import com.github.ruifengho.simplesecurity.jwt.BaseJwtTokenParser;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认jwt存储处理
 */
public class DefaultJwtTokenParser extends BaseJwtTokenParser<DefaultJwtUser> {


    protected static final String USER_ID = "id";

    protected static final String USERNAME = "username";

    protected static final String PERMISSIONS = "permissions";


    //请求头
    protected static final String AUTHORIZATION_HEADER = "Authorization";
    //Bearer header
    protected static final String BEARER = "Bearer ";

    public DefaultJwtTokenParser(SimpleSecurityProperties simpleSecurityProperties) {
        super(simpleSecurityProperties);
    }


    @Override
    protected Map<String, Object> getClaimsMap(DefaultJwtUser user) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(USER_ID, user.getId());
        claims.put(USERNAME, user.getUserName());
        claims.put(PERMISSIONS, user.getPermissions());
        return claims;
    }


    @Override
    protected DefaultJwtUser getUserFromClaims(Claims claims) {
        List<String> permissions = (List<String>) claims.get(PERMISSIONS);
        String userId = (String) claims.get(USER_ID);
        String username = (String) claims.get(USERNAME);
        return DefaultJwtUser.builder().id(userId).userName(username).permissions(permissions).build();
    }

    @Override
    protected String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(header)) {
            throw new SimpleSecurityException("没有找到名为Authorization的header");
        }
        if (!header.startsWith(BEARER)) {
            throw new SimpleSecurityException("token必须以'Bearer '开头");
        }
        if (header.length() <= SEVEN) {
            throw new SimpleSecurityException("token非法，长度 <= 7");
        }
        return header.substring(SEVEN);
    }


}
