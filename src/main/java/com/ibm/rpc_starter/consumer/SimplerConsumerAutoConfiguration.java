package com.ibm.rpc_starter.consumer;

import com.ibm.rpc_starter.model.RpcCommonProperty;
import com.ibm.rpc_starter.serialize.RPCDecoder;
import com.ibm.rpc_starter.serialize.RPCEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/24/14:08
 * @Description: rpc consumer starter
 */
@Configuration
@Slf4j
public class SimplerConsumerAutoConfiguration{

    @Bean
    public  static BeanFactoryPostProcessor initRpcConsumer() throws Exception {
        return new SimpleRpcConsumerPostProcessor();
    }


}
