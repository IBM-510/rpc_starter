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
    //TODO 为什么有两个配置，这个注解配置一次，rpcCommonProperty也要配置一次，到底哪个配置有用

    /**
     * 注册中心类型-默认zk
     * @return
     */
    String registerType() default "zookeeper";

    /**
     * 注册中心地址
     * @return
     */
    String registerAddress() default "127.0.0.1:2181";
}
