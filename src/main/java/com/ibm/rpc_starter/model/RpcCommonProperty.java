package com.ibm.rpc_starter.model;

import com.ibm.rpc_starter.common.enumeration.RpcCommonPropertyDefault;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/22/23:54
 * @Description: RPC通用配置信息，提供用户自定义的功能
 */
@Data
@Component
@ConfigurationProperties(prefix = "ibm.simplerpc")
public class RpcCommonProperty {

    /**
     * 服务提供方地址
     */
    private String serviceAddress= RpcCommonPropertyDefault.DEFAULT_SEVICE_ADDRESS.getMessage();

    /**
     * 注册中心类型
     */
    private String registryType=RpcCommonPropertyDefault.DEFAULT_REGISTRY_TYPE.getMessage();

    /**
     * 注册中心地址
     */
    private String registryAddress=RpcCommonPropertyDefault.DEFAULT_REGISTRY_ADDRESS.getMessage();

    /**
     * 是否开启服务器bean,默认关闭
     */
    private String enable;
}
