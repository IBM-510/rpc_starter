package com.ibm.rpc_starter.serialize;

import com.ibm.rpc_starter.common.enumeration.SerializeTypeEnum;
import com.ibm.rpc_starter.model.RpcCommonProperty;
import com.ibm.rpc_starter.model.SimpleRpcRequest;
import com.ibm.rpc_starter.model.SimpleRpcResponse;
import com.ibm.rpc_starter.serialize.hessian.HessianSerializer;
import com.ibm.rpc_starter.serialize.kryo.KryoSerializer;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/17:16
 * @Description:
 */
@Configuration
@Slf4j
@ConditionalOnClass({RPCDecoder.class,RPCEncoder.class}) // 表示只有指定的class在classpath上时才能被注册
@EnableConfigurationProperties(RpcCommonProperty.class) // 激活@ConfigurationProperties
public class SerializeAutoConfiguration {

    @Autowired
    private RpcCommonProperty rpcCommonProperty;

    /**
     * 创建RPCDecoder对象，注入到Spring容器中
     * @return RPCDecoder的bean
     */
    @Bean
    @Qualifier("rpcDecoder_provider") // 添加限定符
    public RPCDecoder rpcDecoderProvider()
    {
        RPCDecoder rpcDecoder=null;
        if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.HESSIAN_TYPE.getMessage()))
        {
            log.info("The current protocol of the decoder is "+SerializeTypeEnum.HESSIAN_TYPE.getMessage());
            rpcDecoder=new RPCDecoder(new HessianSerializer(), SimpleRpcRequest.class);
        }
        else if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.KRYO_TYPE.getMessage()))
        {
            log.info("The current protocol of the decoder is "+SerializeTypeEnum.KRYO_TYPE.getMessage());
            rpcDecoder=new RPCDecoder(new KryoSerializer(), SimpleRpcRequest.class);
        }
        else
        {
            log.error("the Serialize Type define is fault,the Type is "+rpcCommonProperty.getSerializeType());
            return null;
        }
        return rpcDecoder;
    }

    @Bean
    @Qualifier("rpcDecoder_consumer") // 添加限定符
    public RPCDecoder rpcDecoderConsumer()
    {
        RPCDecoder rpcDecoder=null;
        if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.HESSIAN_TYPE.getMessage()))
        {
            log.info("The current protocol of the decoder is "+SerializeTypeEnum.HESSIAN_TYPE.getMessage());
            rpcDecoder=new RPCDecoder(new HessianSerializer(), SimpleRpcResponse.class);
        }
        else if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.KRYO_TYPE.getMessage()))
        {
            log.info("The current protocol of the decoder is "+SerializeTypeEnum.KRYO_TYPE.getMessage());
            rpcDecoder=new RPCDecoder(new KryoSerializer(), SimpleRpcResponse.class);
        }
        else
        {
            log.error("the Serialize Type define is fault,the Type is "+rpcCommonProperty.getSerializeType());
            return null;
        }
        return rpcDecoder;
    }
    @Bean
    @ConditionalOnMissingBean(RPCEncoder.class) // 当容器没有此bean时，才注册
    public RPCEncoder RpcEncoder()
    {
        RPCEncoder rpcEncoder=null;
        if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.HESSIAN_TYPE.getMessage()))
        {
            log.info("The current protocol of the encoder is "+SerializeTypeEnum.HESSIAN_TYPE.getMessage());
            rpcEncoder=new RPCEncoder(new HessianSerializer());
        }
        else if(rpcCommonProperty.getSerializeType().equals(SerializeTypeEnum.KRYO_TYPE.getMessage()))
        {
            log.info("The current protocol of the encoder is "+SerializeTypeEnum.KRYO_TYPE.getMessage());
            rpcEncoder=new RPCEncoder(new KryoSerializer());
        }
        else
        {
            log.error("the Serialize Type define is fault,the Type is "+rpcCommonProperty.getSerializeType());
            return null;
        }
        return rpcEncoder;
    }


}
