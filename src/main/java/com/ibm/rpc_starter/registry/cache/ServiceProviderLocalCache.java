package com.ibm.rpc_starter.registry.cache;

import com.ibm.rpc_starter.registry.ServiceProviderCache;
import com.ibm.rpc_starter.registry.model.ServiceMetaConfig;
import org.apache.curator.x.discovery.ServiceProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/21:05
 * @Description: 本地缓存实现
 */
public class ServiceProviderLocalCache implements ServiceProviderCache {
    //TODO 使用Caffine或者Ehcache等缓存组件
    /**
     * 本地缓存map
     */
    private Map<String, ServiceProvider<ServiceMetaConfig>> serviceProviderMap = new ConcurrentHashMap<>();

    @Override
    public ServiceProvider<ServiceMetaConfig> queryCache(String serviceName) {
        return serviceProviderMap.get(serviceName);
    }

    @Override
    public void updateCache(String serviceName, ServiceProvider<ServiceMetaConfig> serviceProvider) {
        serviceProviderMap.put(serviceName, serviceProvider);
    }
}
