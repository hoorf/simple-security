package com.github.ruifengho.simplesecurity.annotation;


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



}
