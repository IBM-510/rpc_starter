package com.ibm.rpc_starter.consumer;

import com.ibm.rpc_starter.registry.ServiceProviderCache;
import com.ibm.rpc_starter.registry.ServiceRegistry;
import com.ibm.rpc_starter.registry.cache.ServiceProviderLocalCache;
import com.ibm.rpc_starter.registry.zk.ZkServiceRegistry;
import com.ibm.rpc_starter.serialize.RPCDecoder;
import com.ibm.rpc_starter.serialize.RPCEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/24/10:43
 * @Description: 接口定义了一种创建Bean的机制，允许你在Bean的实例化过程中进行自定义逻辑，这是一个创建简单bean的工厂
 */
@Slf4j
@Component
public class SimpleRpcConsumerFactoryBean implements FactoryBean {

    /**
     * 调用的服务接口类
     */
    private Class<?> interfaceClass;

    /**
     * 服务版本号
     */
    private String serviceVersion;

    /**
     * 注册中心类型
     */
    private String registryType;

    /**
     * 注册中心地址
     */
    private String registryAddress;

    /**
     * 实际的bean
     */
    private Object object;

    @Autowired
    private RPCEncoder rpcEncoder;

    @Autowired
    @Qualifier("rpcDecoder_consumer") // 添加限定符
    private RPCDecoder rpcDecoder;

    /**
     * init方法，通过动态代理生成bean
     *
     * @throws Exception
     */
    public void init() throws Exception {
        ServiceProviderCache serviceProviderCache = new ServiceProviderLocalCache();
        ServiceRegistry zkServiceRegistry = new ZkServiceRegistry(registryAddress, serviceProviderCache);

        //动态代理
        this.object = Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[] {interfaceClass},
                new SimpleRpcInvokeHandler<>(this.serviceVersion, zkServiceRegistry,rpcDecoder,rpcEncoder));
        log.info("SimpleRpcConsumerFactoryBean getObject {}", interfaceClass.getName());
    }

    /**
     * 返回创建的bean实例
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        return this.object;
    }
    /**
     * 创建的bean实例的类型
     *
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    /**
     * 创建的bean实例的作用域
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setRpcEncoder(RPCEncoder rpcEncoder) {
        this.rpcEncoder = rpcEncoder;
    }

    public void setRpcDecoder(RPCDecoder rpcDecoder) {
        this.rpcDecoder = rpcDecoder;
    }
}
