package com.ibm.rpc_starter.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/00:13
 * @Description: RPC请求领域模型
 */
@Data
public class SimpleRpcRequest implements Serializable {
    private static final long serialVersionUID = -6523563004185159591L;

    /**
     * 业务流水号
     */
    private String bizNO;

    /**
     * 服务类名
     */
    private String className;

    /**
     * 服务方法名
     */
    private String methodName;

    /**
     * 服务版本号
     */
    private String serviceVersion;

    /**
     * 参数类型列表
     */
    private Class<?>[] paramTypes;

    /**
     * 参数值列表
     */
    private Object[] paramValues;
}
