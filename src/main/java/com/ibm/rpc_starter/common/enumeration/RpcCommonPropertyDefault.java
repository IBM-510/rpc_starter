package com.ibm.rpc_starter.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/24/00:52
 * @Description: Rpc默认配置
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcCommonPropertyDefault {
    DEFAULT_SEVICE_ADDRESS("127.0.0.1:50001"),
    DEFAULT_REGISTRY_TYPE("zookeeper"),
    DEFAULT_REGISTRY_ADDRESS("127.0.0.1:2181");
    private final String message;
}
