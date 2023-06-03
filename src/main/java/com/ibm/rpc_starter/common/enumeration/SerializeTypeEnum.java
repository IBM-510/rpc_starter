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
    KRYO_TYPE((byte) 0x01, "kyro"),
    HESSIAN_TYPE((byte) 0X02, "hessian");

    private final byte type;
    private final String name;

    public static String getName(byte type) {
        for (SerializeTypeEnum c : SerializeTypeEnum.values()) {
            if (c.getType() == type) {
                return c.name;
            }
        }
        return null;
    }
}
