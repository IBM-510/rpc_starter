package com.ibm.rpc_starter.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/22/23:54
 * @Description: RPC通用配置信息，提供用户自定义的功能
 */

@Component
@ConfigurationProperties(prefix = "ibm.simplerpc")
public class RpcCommonProperty {

    /**
     * 服务提供方地址
     */
    private String serviceAddress= "127.0.0.1:50001";

    /**
     * 注册中心类型
     */
    private String registryType="zookeeper";

    /**
     * 注册中心地址
     */
    private String registryAddress="127.0.0.1:2181";

    /**
     * 序列化工具类型
     */
    private String serializeType;

    /**
     * 是否开启服务器bean,默认关闭
     */
    private String enable;
    public String getServiceAddress() {
        return serviceAddress;
    }

    public String getRegistryType() {
        return registryType;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public String getSerializeType() {
        return serializeType;
    }

    public String getEnable() {
        return enable;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setSerializeType(String serializeType) {
        this.serializeType = serializeType;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
