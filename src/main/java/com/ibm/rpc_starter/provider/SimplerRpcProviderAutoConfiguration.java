package com.ibm.rpc_starter.provider;

import com.ibm.rpc_starter.model.RpcCommonProperty;
import com.ibm.rpc_starter.registry.ServiceProviderCache;
import com.ibm.rpc_starter.registry.ServiceRegistry;
import com.ibm.rpc_starter.registry.cache.ServiceProviderLocalCache;
import com.ibm.rpc_starter.registry.zk.ZkServiceRegistry;
import com.ibm.rpc_starter.serialize.RPCProviderDecoder;
import com.ibm.rpc_starter.serialize.RPCProviderEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/23:44
 * @Description: rpc provider starter
 */
@Configuration
@Slf4j
@ConditionalOnClass(SimpleRpcProviderBean.class) // 表示只有指定的class在classpath上时才能被注册
@EnableConfigurationProperties(RpcCommonProperty.class) // 激活@ConfigurationProperties
public class SimplerRpcProviderAutoConfiguration {

    @Autowired
    private RpcCommonProperty rpcCommonProperty;

    @Autowired
    private RPCProviderEncoder rpcEncoder;

    @Autowired
    private RPCProviderDecoder rpcDecoder;

    @Bean
    @ConditionalOnProperty(name = "ibm.simplerpc.provider-enable", matchIfMissing = false)
    public SimpleRpcProviderBean initRpcProvider() throws Exception {

        log.info("===================SimplerRpcProviderAutoConfiguration init，rpcCommonProperty=" + rpcCommonProperty.toString());
        ServiceProviderCache serviceProviderCache = new ServiceProviderLocalCache();
        ServiceRegistry zkServiceRegistry = new ZkServiceRegistry(rpcCommonProperty.getRegistryAddress(), serviceProviderCache);

        return new SimpleRpcProviderBean(rpcCommonProperty.getServiceAddress(), zkServiceRegistry,rpcEncoder,rpcDecoder);
    }

}
