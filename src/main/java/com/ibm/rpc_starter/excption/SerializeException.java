package com.ibm.rpc_starter.excption;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/13:46
 * @Description:
 */
public class SerializeException extends RuntimeException{
    public SerializeException(String message) {
        super(message);
    }
}
