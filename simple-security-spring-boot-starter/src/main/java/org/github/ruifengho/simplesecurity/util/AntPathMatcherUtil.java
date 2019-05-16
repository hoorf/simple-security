package org.github.ruifengho.simplesecurity.util;

import org.github.ruifengho.simplesecurity.define.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class AntPathMatcherUtil {
    private static final Logger logger = LoggerFactory.getLogger(AntPathMatcherUtil.class);

    private static final String MATCH_ALL = "/**";
    private static final AntPathMatcher MATCHER = new AntPathMatcher();


    /**
     * 判断request中的url和method是否和pattern一致
     *
     * @param request
     * @param httpMethod
     * @param pattern
     * @return
     */
    public static boolean match(HttpServletRequest request, HttpMethod httpMethod, String pattern) {
        boolean methodMatches = matchMethod(request, httpMethod);
        boolean pathMatches = matchPath(request, pattern);

        logger.info("match begins. {} {}, httpMethod = {}, pattern = {}, methodMatch = {}, pathMatches = {}",
                request.getMethod(), getRequestPath(request),
                httpMethod, pattern, methodMatches, pathMatches
        );
        return methodMatches && pathMatches;
    }

    private static boolean matchMethod(HttpServletRequest request, HttpMethod httpMethod) {

        logger.debug("method match begins. {} {}, httpMethod = {}",
                request.getMethod(), getRequestPath(request), httpMethod);
        if (HttpMethod.ANY == httpMethod) {
            return true;
        }
        String method = request.getMethod();
        HttpMethod sourceHttpMethod = HttpMethod.valueOf(method);
        return httpMethod != null && httpMethod == sourceHttpMethod;

    }

    /**
     * 判断路径是否匹配
     *
     * @param request
     * @param pattern
     * @return
     */
    private static boolean matchPath(HttpServletRequest request, String pattern) {
        String url = getRequestPath(request);
        if (MATCH_ALL.equals(pattern)) {
            return true;
        }
        return MATCHER.match(pattern, url);
    }

    /**
     * 获取请求路径
     *
     * @param request
     * @return
     */
    private static String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (null != pathInfo) {
            url = StringUtils.hasLength(url) ? url + pathInfo : pathInfo;
        }
        return url;
    }
}
