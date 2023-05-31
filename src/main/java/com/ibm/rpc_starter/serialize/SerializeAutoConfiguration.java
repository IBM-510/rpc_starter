package com.ibm.rpc_starter.serialize;

import com.ibm.rpc_starter.common.enumeration.SerializeTypeEnum;
import com.ibm.rpc_starter.model.RpcCommonProperty;
import com.ibm.rpc_starter.model.SimpleRpcRequest;
import com.ibm.rpc_starter.model.SimpleRpcResponse;
import com.ibm.rpc_starter.serialize.hessian.HessianSerializer;
import com.ibm.rpc_starter.serialize.kryo.KryoSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/17:16
 * @Description:
 */
@Configuration
@Slf4j
@ConditionalOnClass({RPCConsumerDecoder.class, RPCConsumerEncoder.class}) // 表示只有指定的class在classpath上时才能被注册
@EnableConfigurationProperties(RpcCommonProperty.class) // 激活@ConfigurationProperties
public class SerializeAutoConfiguration {

    @Autowired
    private RpcCommonProperty rpcCommonProperty;

    @Bean
    public RPCConsumerDecoder rpcDecoderConsumer()
    {
        RPCConsumerDecoder rpcDecoder=null;
        if(rpcCommonProperty.getSerializeType()==null) {
            log.info("No serialization tool type configured.");
            return new RPCConsumerDecoder();
        }
        if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.HESSIAN_TYPE.getMessage()))
        {
            log.info("The current protocol of the decoder is "+SerializeTypeEnum.HESSIAN_TYPE.getMessage());
            rpcDecoder=new RPCConsumerDecoder(new HessianSerializer(), SimpleRpcResponse.class);
        }
        else if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.KRYO_TYPE.getMessage()))
        {
            log.info("The current protocol of the decoder is "+SerializeTypeEnum.KRYO_TYPE.getMessage());
            rpcDecoder=new RPCConsumerDecoder(new KryoSerializer(), SimpleRpcResponse.class);
        }
        else
        {
            log.error("the Serialize Type define is fault,the Type is "+rpcCommonProperty.getSerializeType());
            return null;
        }
        return rpcDecoder;
    }
    @Bean
    public RPCConsumerEncoder RpcEncoder()
    {
        RPCConsumerEncoder rpcEncoder=null;
        if(rpcCommonProperty.getSerializeType()==null) {
            log.info("No serialization tool type configured.");
            return new RPCConsumerEncoder();
        }
        if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.HESSIAN_TYPE.getMessage()))
        {
            log.info("The current protocol of the encoder is "+SerializeTypeEnum.HESSIAN_TYPE.getMessage());
            rpcEncoder=new RPCConsumerEncoder(new HessianSerializer());
        }
        else if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.KRYO_TYPE.getMessage()))
        {
            log.info("The current protocol of the encoder is "+SerializeTypeEnum.KRYO_TYPE.getMessage());
            rpcEncoder=new RPCConsumerEncoder(new KryoSerializer());
        }
        else
        {
            log.error("the Serialize Type define is fault,the Type is "+rpcCommonProperty.getSerializeType());
            return null;
        }
        return rpcEncoder;
    }
    @Bean
    public RPCProviderEncoder rpcProviderEncoder()
    {
        List<Serializer>serializers=new ArrayList<>();
        serializers.add(SerializeTypeEnum.KRYO_TYPE.getType(),new KryoSerializer());
        serializers.add(SerializeTypeEnum.HESSIAN_TYPE.getType(),new HessianSerializer());
        return new RPCProviderEncoder(serializers);
    }
    @Bean
    public RPCProviderDecoder rpcProviderDecoder()
    {
        List<Serializer>serializers=new ArrayList<>();
        serializers.add(SerializeTypeEnum.KRYO_TYPE.getType(),new KryoSerializer());
        serializers.add(SerializeTypeEnum.HESSIAN_TYPE.getType(),new HessianSerializer());
        return new RPCProviderDecoder(serializers,SimpleRpcRequest.class);
    }


}
