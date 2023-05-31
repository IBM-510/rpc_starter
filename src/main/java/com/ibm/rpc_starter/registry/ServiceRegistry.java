package com.ibm.rpc_starter.registry;

import com.ibm.rpc_starter.registry.model.ServiceMetaConfig;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/18:20
 * @Description: 注册中心服务接口定义
 */
public interface ServiceRegistry {
    //TODO 处理一个接口有多个实现类的情况

    /**
     * 注册服务
     *
     * @param serviceMetaConfig 服务元数据配置
     * @throws Exception
     */
    void register(ServiceMetaConfig serviceMetaConfig) throws Exception;

    /**
     * 取消注册服务
     *
     * @param serviceMetaConfig 服务元数据配置
     * @throws Exception
     */
    void unRegister(ServiceMetaConfig serviceMetaConfig) throws Exception;

    /**
     * 服务发现
     *
     * @param serviceName 服务名
     * @return
     * @throws Exception
     */
    ServiceMetaConfig discovery(String serviceName) throws Exception;
}
