package com.github.ruifengho.simplesecurity.autoconfigure;

import com.github.ruifengho.simplesecurity.define.PermissionExpression;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "simple-security")
public class SimpleSecurityProperties {

    private Jwt jwt = new Jwt();
    private List<PermissionExpression> expressionList = new ArrayList();


    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public List<PermissionExpression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<PermissionExpression> expressionList) {
        this.expressionList = expressionList;
    }

    public static class Jwt {
        /**
         * secret
         */
        private String secret = "simple-security";

        /**
         * token的有效时间(秒)，默认2周
         */
        private Long expirationInSecond = 1209600L;

        /**
         * 加签的算法，默认sha512
         */
        private SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public Long getExpirationInSecond() {
            return expirationInSecond;
        }

        public void setExpirationInSecond(Long expirationInSecond) {
            this.expirationInSecond = expirationInSecond;
        }

        public SignatureAlgorithm getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(SignatureAlgorithm algorithm) {
            this.algorithm = algorithm;
        }
    }
}
