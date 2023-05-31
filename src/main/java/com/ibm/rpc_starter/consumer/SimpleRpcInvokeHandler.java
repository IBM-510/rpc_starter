package com.ibm.rpc_starter.consumer;

import com.ibm.rpc_starter.common.enumeration.SerializeTypeEnum;
import com.ibm.rpc_starter.model.SimpleRpcRequest;
import com.ibm.rpc_starter.model.SimpleRpcResponse;
import com.ibm.rpc_starter.registry.ServiceRegistry;
import com.ibm.rpc_starter.serialize.RPCConsumerDecoder;
import com.ibm.rpc_starter.serialize.RPCConsumerEncoder;
import com.ibm.rpc_starter.serialize.hessian.HessianSerializer;
import com.ibm.rpc_starter.serialize.kryo.KryoSerializer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/24/13:14
 * @Description: RPC调用动态代理handler实现
 */
@Slf4j
public class SimpleRpcInvokeHandler<T> implements InvocationHandler {

    /**
     * 服务版本号
     */
    private String serviceVersion;

    /**
     * 注册中心
     */
    private ServiceRegistry serviceRegistry;
    private RPCConsumerDecoder rpcDecoder;
    private RPCConsumerEncoder rpcEncoder;

    public SimpleRpcInvokeHandler() {

    }

    public SimpleRpcInvokeHandler(String serviceVersion, ServiceRegistry serviceRegistry, RPCConsumerDecoder rpcDecoder, RPCConsumerEncoder rpcEncoder) {
        this.serviceVersion = serviceVersion;
        this.serviceRegistry = serviceRegistry;
        this.rpcDecoder=rpcDecoder;
        this.rpcEncoder=rpcEncoder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SimpleRpcRequest simpleRpcRequest = new SimpleRpcRequest();
        simpleRpcRequest.setBizNO(UUID.randomUUID().toString());
        simpleRpcRequest.setClassName(method.getDeclaringClass().getName());
        simpleRpcRequest.setServiceVersion(this.serviceVersion);
        simpleRpcRequest.setMethodName(method.getName());
        simpleRpcRequest.setParamTypes(method.getParameterTypes());
        simpleRpcRequest.setParamValues(args);
        if(rpcEncoder.getSerializer() instanceof HessianSerializer)
            simpleRpcRequest.setSerializeType(SerializeTypeEnum.HESSIAN_TYPE.getType());
        else if(rpcEncoder.getSerializer() instanceof KryoSerializer)
            simpleRpcRequest.setSerializeType(SerializeTypeEnum.KRYO_TYPE.getType());
        else
            log.error("the type of the serialize is unknown");

        log.info("begin simpleRpcRequest=" + simpleRpcRequest.toString());

        SimpleRpcConsumerNettyHandler simpleRpcConsumerNettyHandler = new SimpleRpcConsumerNettyHandler(this.serviceRegistry,rpcDecoder,rpcEncoder);
        SimpleRpcResponse simpleRpcResponse = simpleRpcConsumerNettyHandler.sendRpcRequest(simpleRpcRequest);

        log.info("result simpleRpcResponse=" + simpleRpcResponse);
        return simpleRpcResponse.getData();
    }
}
