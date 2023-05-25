package com.ibm.rpc_starter.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/24/00:18
 * @Description: RPC provider注解
 */
@Retention(RetentionPolicy.RUNTIME)
//注解打在类上
@Target(ElementType.TYPE)
@Component
public @interface SimpleRpcProvider {
    Class<?> serviceInterface() default Object.class;
    String serviceVersion() default "1.0.0";
}
