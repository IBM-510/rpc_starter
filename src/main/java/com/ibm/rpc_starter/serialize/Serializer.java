package com.ibm.rpc_starter.serialize;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/13:36
 * @Description:
 */
public interface Serializer {
    /**
     * 序列化
     * @param obj 要序列化的对象
     * @return 字节数组
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     * @param bytes 序列化后的字节数组
     * @param clazz 类
     * @return 反序列化的对象
     * @param <T>
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
