package com.github.ruifengho.simplesecurity.annotation.support;

import com.github.ruifengho.simplesecurity.annotation.ApiDecrypt;
import com.github.ruifengho.simplesecurity.autoconfigure.SimpleSecurityProperties;
import com.github.ruifengho.simplesecurity.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.*;
import java.lang.reflect.Type;
import java.util.stream.Collectors;


@RestControllerAdvice
public class EncryptRequestBodyAdvice implements RequestBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(EncryptRequestBodyAdvice.class);

    @Autowired
    private SimpleSecurityProperties simpleSecurityProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(ApiDecrypt.class) && simpleSecurityProperties.getJwe().isOpen();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return decrypt(inputMessage);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }


    /**
     * 解密
     *
     * @param inputMessage
     * @return
     * @throws IOException
     */
    private HttpInputMessage decrypt(HttpInputMessage inputMessage) {

        String content = null;
        String decryptBody = null;
        try {
            content = new BufferedReader(new InputStreamReader(inputMessage.getBody()))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            decryptBody = RSAUtil.decrypt(content, simpleSecurityProperties.getJwe().getPrivateKey());
            if(simpleSecurityProperties.getJwe().isShowLog()){
                logger.info("decrypt after : {} ,before : {}",decryptBody,content);
            }
        } catch (Exception e) {
            logger.error("decrypt error => {}", content);
            logger.error("error for ", e);
        }
        String body = decryptBody;

        return new HttpInputMessage() {

            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(body.getBytes());
            }
        };

    }
}
