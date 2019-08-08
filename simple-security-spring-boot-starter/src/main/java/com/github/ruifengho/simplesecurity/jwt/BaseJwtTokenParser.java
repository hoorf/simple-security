package com.github.ruifengho.simplesecurity.jwt;

import com.github.ruifengho.simplesecurity.autoconfigure.SimpleSecurityProperties;
import com.github.ruifengho.simplesecurity.exception.SimpleSecurityException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public abstract class BaseJwtTokenParser<T extends JwtUser> implements JwtTokenParser<T>{

    protected static final int SEVEN = 7;

    protected static final String SIMPLE_SECURITY_REQ_ATTR_USER = "simple-security-user";

    private static final Logger logger = LoggerFactory.getLogger(BaseJwtTokenParser.class);

    protected SimpleSecurityProperties simpleSecurityProperties;


    public BaseJwtTokenParser(SimpleSecurityProperties simpleSecurityProperties) {
        this.simpleSecurityProperties = simpleSecurityProperties;
    }

    public final Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(simpleSecurityProperties.getJwt().getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            logger.error("token解析错误", e);
            throw new SimpleSecurityException("Token invalided.", e);
        }
    }

    /**
     * 获取token的过期时间
     *
     * @param token token
     * @return 过期时间
     */
    public final Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        return new Date().after(getExpirationDateFromToken(token));
    }

    private Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + simpleSecurityProperties.getJwt().getExpirationInSecond() * 1000);
    }

    public final String generateToken(T user) {
        Map<String, Object> claims = getClaimsMap(user);
        Date createTime = new Date();
        Date expirationTime = getExpirationTime();

        byte[] keyBytes = this.simpleSecurityProperties.getJwt().getSecret().getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder().setClaims(claims).setIssuedAt(createTime).setExpiration(expirationTime).signWith(key).compact();

    }

    protected abstract Map<String, Object> getClaimsMap(T user);

    public final Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


    /**
     * 从request中获取用户信息
     *
     * @return 用户
     */
    public final T getUser() {
        try {
            HttpServletRequest request = getRequest();
            String token = getTokenFromRequest(request);
            Boolean isValid = validateToken(token);
            if (!isValid) {
                return null;
            }
            Object obj = request.getAttribute(SIMPLE_SECURITY_REQ_ATTR_USER);
            if (obj != null) {
                return (T) obj;
            }
            T user = getUserFromToken(token);
            request.setAttribute(SIMPLE_SECURITY_REQ_ATTR_USER, user);
            return user;
        } catch (Exception e) {
            throw new SimpleSecurityException(e);
        }
    }

    /**
     * 从token中获取用户信息
     *
     * @param token jwt
     * @return 用户
     */
    public final T getUserFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return getUserFromClaims(claims);
    }


    /**
     * 如何从jwt中返回user
     *
     * @param claims jwtClaims
     * @return 用户
     */
    protected abstract T getUserFromClaims(Claims claims);

    /**
     * 如何从request中获取token
     *
     * @param request 请求
     * @return token
     */
    protected abstract String getTokenFromRequest(HttpServletRequest request);

    protected final HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new SimpleSecurityException("requestAttributes为null");
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();

    }
}
