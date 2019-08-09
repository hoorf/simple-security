package com.github.ruifengho.simplesecurity.autoconfigure;

import com.github.ruifengho.simplesecurity.define.PermissionExpression;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "simple-security")
public class SimpleSecurityProperties {

    private Jwt jwt = new Jwt();
    private EncryptApi apiEncrypt = new EncryptApi();
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

    public EncryptApi getApiEncrypt() {
        return apiEncrypt;
    }

    public void setApiEncrypt(EncryptApi apiEncrypt) {
        this.apiEncrypt = apiEncrypt;
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

    public static class EncryptApi {
        private boolean showLog;
        private RSA rsa = new RSA();
        private DES des = new DES();

        public boolean isShowLog() {
            return showLog;
        }

        public void setShowLog(boolean showLog) {
            this.showLog = showLog;
        }

        public RSA getRsa() {
            return rsa;
        }

        public void setRsa(RSA rsa) {
            this.rsa = rsa;
        }

        public DES getDes() {
            return des;
        }

        public void setDes(DES des) {
            this.des = des;
        }
    }

    public static class DES{
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class AES{

    }



    public static class RSA {
        private String privateKey;
        private String publicKey;
        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
    }

}
