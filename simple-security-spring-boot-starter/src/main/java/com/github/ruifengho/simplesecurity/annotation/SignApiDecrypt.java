package com.github.ruifengho.simplesecurity.annotation;


import com.github.ruifengho.simplesecurity.decrypt.SignApiDecryptFunction;

import java.lang.annotation.*;
import java.util.Map;
import java.util.function.Function;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignApiDecrypt {

    /**
     * 签名字段
     *
     * @return
     */
    String sign() default "sign";


    /**
     * 解密类
     *
     * @return
     */
    Class<? extends SignApiDecryptFunction> decrptyClass() default SignApiDecryptFunction.class;

}
