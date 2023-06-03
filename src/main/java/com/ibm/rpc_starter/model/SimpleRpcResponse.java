package com.ibm.rpc_starter.model;

import com.ibm.rpc_starter.common.enumeration.RpcResponseCodeEnum;
import lombok.*;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/13:28
 * @Description: rpc处理结果
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SimpleRpcResponse<T> implements Serializable {
    private static final long serialVersionUID = 7306531831668743451L;

    /**
     * 业务流水号
     */
    private String bizNO;

    /**
     * response code
     */
    private Integer code;

    /**
     * response message
     */
    private String message;

    private String requestId;

    /**
     * 实际结果
     */
    private Object data;

    public static <T> SimpleRpcResponse<T> success(T data, String requestId) {
        SimpleRpcResponse<T> response = new SimpleRpcResponse<>();
        response.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());
        response.setRequestId(requestId);
        if (null != data) {
            response.setData(data);
        }
        return response;
    }

    public static <T> SimpleRpcResponse<T> fail(RpcResponseCodeEnum rpcResponseCodeEnum) {
        SimpleRpcResponse<T> response = new SimpleRpcResponse<>();
        response.setCode(rpcResponseCodeEnum.getCode());
        response.setMessage(rpcResponseCodeEnum.getMessage());
        return response;
    }
}
