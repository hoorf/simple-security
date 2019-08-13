package com.github.ruifengho.simplesecurity.annotation.support;

import com.github.ruifengho.simplesecurity.annotation.SignApiDecrypt;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SignApiResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(SignApiResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SignApiDecrypt.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Class<?> targetClass = parameter.getNestedParameterType();
        Object result = targetClass.newInstance();
        BeanUtils.populate(result, webRequest.getParameterMap());
        return result;
    }


}
