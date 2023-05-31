package com.ibm.rpc_starter.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    @ConditionalOnProperty(name = "ibm.simplerpc.consumer-enable", matchIfMissing = true)
    public  static BeanFactoryPostProcessor initRpcConsumer() throws Exception {
        return new SimpleRpcConsumerPostProcessor();
    }


}
