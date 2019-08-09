package com.github.ruifengho.simplesecurity.annotation.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ruifengho.simplesecurity.annotation.RsaApiEncrypt;
import com.github.ruifengho.simplesecurity.autoconfigure.SimpleSecurityProperties;
import com.github.ruifengho.simplesecurity.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class RsaEncryptResponseBodyAdvice implements ResponseBodyAdvice {
    private static final Logger logger = LoggerFactory.getLogger(RsaEncryptResponseBodyAdvice.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpleSecurityProperties simpleSecurityProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(RsaApiEncrypt.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String result;
        try {
            String value = objectMapper.writeValueAsString(body);
            result = RSAUtil.encrypt(value, simpleSecurityProperties.getApiEncrypt().getRsa().getPublicKey());
            if(simpleSecurityProperties.getApiEncrypt().isShowLog()){
                logger.info("encrypt before : {} ,after : {}",value,result);
            }
            return result;
        } catch (Exception e) {
            logger.error("Encrypt error", e);
        }


        return body;
    }
}
