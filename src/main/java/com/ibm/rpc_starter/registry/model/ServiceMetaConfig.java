package com.ibm.rpc_starter.registry.model;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/18:21
 * @Description: 服务元数据配置领域模型
 */
@Data
public class ServiceMetaConfig {

    /**
     * 服务名
     */
    private String name;

    /**
     * 服务版本
     */
    private String version;

    /**
     * 服务地址
     */
    private String address;

    /**
     * 服务端口
     */
    private Integer port;
}
