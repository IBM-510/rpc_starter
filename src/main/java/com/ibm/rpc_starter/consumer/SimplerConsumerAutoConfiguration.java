package com.ibm.rpc_starter.consumer;

import com.ibm.rpc_starter.serialize.RPCDecoder;
import com.ibm.rpc_starter.serialize.RPCEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/24/14:08
 * @Description: rpc consumer starter
 */
@Configuration
@Slf4j
public class SimplerConsumerAutoConfiguration {


    @Bean
    public static BeanFactoryPostProcessor initRpcConsumer(RPCDecoder rpcDecoder,RPCEncoder rpcEncoder) throws Exception {
        return new SimpleRpcConsumerPostProcessor(rpcDecoder,rpcEncoder);
    }


}
