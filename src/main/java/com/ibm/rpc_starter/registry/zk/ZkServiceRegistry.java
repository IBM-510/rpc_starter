package com.ibm.rpc_starter.registry.zk;

import com.ibm.rpc_starter.common.util.ServiceUtils;
import com.ibm.rpc_starter.registry.ServiceProviderCache;
import com.ibm.rpc_starter.registry.ServiceRegistry;
import com.ibm.rpc_starter.registry.model.ServiceMetaConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.*;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RoundRobinStrategy;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/21:18
 * @Description: 服务注册中心-zk实现
 */
public class ZkServiceRegistry implements ServiceRegistry {

    /**
     * zk base path
     */
    private final static String ZK_BASE_PATH = "/simplerpc";

    /**
     * serviceProvider锁
     */
    private final Object lock = new Object();

    /**
     * zk framework client
     */
    private CuratorFramework client;

    /**
     * 服务发现
     */
    private ServiceDiscovery<ServiceMetaConfig> serviceDiscovery;

    /**
     * serviceProvider缓存
     */
    private ServiceProviderCache serviceProviderCache;

    /**
     * 构造函数
     * @param address 注册地址
     * @param serviceProviderCache 服务缓存组件
     * @throws Exception
     */
    public ZkServiceRegistry(String address, ServiceProviderCache serviceProviderCache) throws Exception
    {
        this.client= CuratorFrameworkFactory.newClient(address,new ExponentialBackoffRetry(1000, 3));
        this.client.start();

        this.serviceProviderCache = serviceProviderCache;

        JsonInstanceSerializer<ServiceMetaConfig> serializer=new JsonInstanceSerializer<>(ServiceMetaConfig.class);
        serviceDiscovery= ServiceDiscoveryBuilder.builder(ServiceMetaConfig.class)
                .client(this.client)
                .serializer(serializer)
                .basePath(ZK_BASE_PATH)
                .build();
        serviceDiscovery.start();
    }
    @Override
    public void register(ServiceMetaConfig serviceMetaConfig) throws Exception {
        ServiceInstanceBuilder<ServiceMetaConfig> serviceInstanceBuilder = ServiceInstance.builder();
        ServiceInstance<ServiceMetaConfig> serviceInstance = serviceInstanceBuilder
                .name(ServiceUtils.buildServiceKey(serviceMetaConfig.getName(), serviceMetaConfig.getVersion()))
                .address(serviceMetaConfig.getAddress())
                .port(serviceMetaConfig.getPort())
                .payload(serviceMetaConfig)
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public void unRegister(ServiceMetaConfig serviceMetaConfig) throws Exception {
        ServiceInstanceBuilder<ServiceMetaConfig> serviceInstanceBuilder = ServiceInstance.builder();
        ServiceInstance<ServiceMetaConfig> serviceInstance = serviceInstanceBuilder
                .name(ServiceUtils.buildServiceKey(serviceMetaConfig.getName(), serviceMetaConfig.getVersion()))
                .address(serviceMetaConfig.getAddress())
                .port(serviceMetaConfig.getPort())
                .payload(serviceMetaConfig)
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();

        serviceDiscovery.unregisterService(serviceInstance);
    }

    @Override
    public ServiceMetaConfig discovery(String serviceName) throws Exception {
        //先读缓存
        ServiceProvider<ServiceMetaConfig> serviceProvider = serviceProviderCache.queryCache(serviceName);
        //缓存miss，需要调serviceDiscovery
        if (serviceProvider == null) {
            synchronized (lock) {
                serviceProvider = serviceDiscovery.serviceProviderBuilder()
                        .serviceName(serviceName)
                        .providerStrategy(new RoundRobinStrategy<>())
                        .build();
                serviceProvider.start();

                //更新缓存
                serviceProviderCache.updateCache(serviceName, serviceProvider);
            }
        }
        ServiceInstance<ServiceMetaConfig> serviceInstance = serviceProvider.getInstance();
        return serviceInstance != null ? serviceInstance.getPayload() : null;
    }
}
