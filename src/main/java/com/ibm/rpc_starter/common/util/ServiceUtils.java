package com.ibm.rpc_starter.common.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/18:16
 * @Description: 服务相关通用工具类
 */
public class ServiceUtils {

    /**
     * 分隔符
     */
    public final static String SPLIT_CHAR = ":";

    /**
     * 服务唯一标识key组装
     *
     * @param serviceName 服务名
     * @param serviceVersion 服务版本
     * @return
     */
    public final static String buildServiceKey(String serviceName, String serviceVersion) {
        return String.join(SPLIT_CHAR, serviceName, serviceVersion);
    }
}
