package com.ibm.rpc_starter.registry;

import com.ibm.rpc_starter.registry.model.ServiceMetaConfig;
import org.apache.curator.x.discovery.ServiceProvider;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/18:34
 * @Description:
 */
public interface ServiceProviderCache {

    /**
     * 查询缓存
     * @param serviceName
     * @return
     */
    ServiceProvider<ServiceMetaConfig> queryCache(String serviceName);

    /**
     * 更新缓存
     *
     * @param serviceName 服务名
     * @param serviceProvider 服务provider
     * @return
     */
    void updateCache(String serviceName, ServiceProvider<ServiceMetaConfig> serviceProvider);
}
