package com.ibm.rpc_starter.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/15:30
 * @Description: 序列化工具枚举类
 */
@AllArgsConstructor
@Getter
@ToString
public enum SerializeTypeEnum {
    KRYO_TYPE("kryo",0),
    HESSIAN_TYPE("hessian",1);
    private final String message;
    private final int type;
}
