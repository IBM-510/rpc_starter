package com.ibm.rpc_starter.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/13:28
 * @Description: rpc处理结果
 */
@Data
public class SimpleRpcResponse implements Serializable {
    private static final long serialVersionUID = 7306531831668743451L;

    /**
     * 业务流水号
     */
    private String bizNO;

    /**
     * 错误结果提示消息
     */
    private String msg;

    /**
     * 实际结果
     */
    private Object data;
}
