package org.github.ruifengho.simplesecurity.demo;

import com.github.ruifengho.simplesecurity.util.RSAUtil;
import com.github.ruifengho.simplesecurity.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

        String privateKey = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAM1ngAZgLJKjevXImx5zl7T9huBGSydGBRhHgEwPRubp1FRyOHFkZ3vYPlUUo+DiFrUmnZGTab8Yua1CRUzwXxHDreWy+s0iyJByB6rZOyhJpaEariJP3ZzZ1ojUSE73Hp6h6lDPyjZjEDCeBIehD7HvdTzjR7AZm2tE21H4u1ovAgMBAAECgYEAt0Lozsdv/UUup6vb+kdXy3uHowIUe23Vjdv9c98Ne2iUsONXyJO0YKKGupI3xKTzbZyOaRwE9SKyRwshmYu8xRuJKeXttkC/E3COLNC+NMK+yc8/4B5vCo2Lv66NRNWi3gMEFrPEiqXxcTY1RA3oGKdncWOV1lW6zKbMslBl1gECQQDlX+MyQ5dx6rRvzwCv6CZlCT4b5QNZRqotbO6WHPHKhoGlDxwMh0yArC/7n8MOuvqYp+wsINfkfO2FcSs0z+pPAkEA5T9PdRbGhvzxjQUDjP441/OCfdme4hGIPxsfeBRfXjnzAGtuzOEPUMXDBr9wQHVmrY8tSqWOx2E9MGaBANP6IQJBAKjrnN3/eSCjwI423EKvrNbrn5nbZkPxDpK1jl9uoYKM3B06q2VunqCELjIYzgWjHboesIcvCM90UVfcp/0UwXECQQCLQv593qwTbK1AQX96syTM6vymfwDpzES8MTnYtGeK0iEwbBMPGtk29CwZeUoznh4V6RhWdGRb8lI9iMdtPViBAkEAgD1vg34I9Ng6hk3SAouZ4MhhhTi2s0LBJcpU6KH+mOVCrPoVuv5dte4xvwWB05Mh3tpyshor3Ooy5TIC0FppRw==";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNZ4AGYCySo3r1yJsec5e0/YbgRksnRgUYR4BMD0bm6dRUcjhxZGd72D5VFKPg4ha1Jp2Rk2m/GLmtQkVM8F8Rw63lsvrNIsiQcgeq2TsoSaWhGq4iT92c2daI1EhO9x6eoepQz8o2YxAwngSHoQ+x73U840ewGZtrRNtR+LtaLwIDAQAB";

        Order order = new Order();
        order.setName("测试单001");
        order.setOrderNo("B00001");
        logger.info("加密前{}",order);

        logger.info("DES测试加密");
        logger.info(SignUtil.sign(order,"qazwsxed"));
        logger.info("RSA测试加密");

        logger.info(RSAUtil.encrypt("{orderNo='B00001', name='测试单001'}",publicKey));
    }
}
