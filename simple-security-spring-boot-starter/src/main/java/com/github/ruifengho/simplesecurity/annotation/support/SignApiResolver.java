package com.github.ruifengho.simplesecurity.annotation.support;

import com.github.ruifengho.simplesecurity.annotation.SignApiDecrypt;
import com.github.ruifengho.simplesecurity.autoconfigure.SimpleSecurityProperties;
import com.github.ruifengho.simplesecurity.util.DesUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SignApiResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(SignApiResolver.class);


    @Autowired
    private SimpleSecurityProperties simpleSecurityProperties;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SignApiDecrypt.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        SignApiDecrypt signApiDecrypt = parameter.getParameterAnnotation(SignApiDecrypt.class);

        Class<?> targetClass = parameter.getNestedParameterType();
        Object result = targetClass.newInstance();
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        if (!validate(parameterMap, signApiDecrypt)) {
            throw new IllegalArgumentException(" validate sign error");
        }
        BeanUtils.populate(result, parameterMap);
        return result;
    }

    private boolean validate(Map<String, String[]> parameterMap, SignApiDecrypt signApiDecrypt) {

        boolean needLog = simpleSecurityProperties.getApiEncrypt().isShowLog();
        TreeMap<String, String[]> sortMap = new TreeMap<>(parameterMap);
        String sign = sortMap.get(signApiDecrypt.sign())[0];
        sortMap.remove(signApiDecrypt.sign());
        StringBuilder sb = new StringBuilder();
        System.err.println(sortMap);
        for (String key : sortMap.keySet()) {
            sb.append(key).append("=").append(sortMap.get(key)[0]).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        String str = sb.toString();

        String key = simpleSecurityProperties.getApiEncrypt().getDes().getKey();

        if (needLog) {
            logger.info("before encrypt string {} , encrypt key{}", str, key);
        }

        try {
            String encrypt = DesUtil.encrypt(str, key);
            if (needLog) {
                logger.info("after encrypt string {} ", encrypt);
            }
            if (encrypt.equals(sign)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }


}
