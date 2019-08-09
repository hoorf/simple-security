package com.github.ruifengho.simplesecurity.annotation;

import java.lang.annotation.*;
import java.util.LinkedList;
import java.util.function.Function;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiDecrypt {

}
