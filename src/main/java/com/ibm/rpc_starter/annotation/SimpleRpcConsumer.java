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
 * @Date: 2023/05/24/00:49
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
//注解打在属性上
@Target(ElementType.FIELD)
@Component
public @interface SimpleRpcConsumer {

    /**
     * 服务版本号
     * @return
     */
    String serviceVersion() default "1.0.0";

}
