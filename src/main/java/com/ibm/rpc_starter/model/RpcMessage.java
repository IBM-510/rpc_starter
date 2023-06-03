package com.ibm.rpc_starter.model;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/06/02/23:02
 * @Description: RPC协议的dto对象
 */
/**
 协议消息格式：
 +--------------+--------------+------------+--------------+-----------------+
 | Magic Code | Version | Full Length | MessageType | Codec |
 | (4 bytes) | (1 byte) | (4 bytes) | (1 byte) | (1 byte) |
 +--------------+--------------+------------+--------------+-----------------+
 | Request ID ... |
 | (4 bytes) |
 +------------------------------------------------------------------------+
 | Body ... |
 +------------------------------------------------------------------------+
 字段说明：
 Magic Code: 魔数，占用 4 个字节（int），用于标识协议的起始。
 Version: 协议版本，占用 1 个字节（byte），表示协议的版本号。
 Full Length: 消息总长度，占用 4 个字节（int），表示整个消息的字节长度，包括头部和 body 的长度。
 MessageType: 消息类型，占用 1 个字节（byte），用于标识消息的类型。
 Codec: 编码器类型，占用 1 个字节（byte），表示 body 的编码器类型。
 Request ID: 请求 ID，占用 4 个字节（int），用于标识请求的唯一 ID。
 Body: 消息体，变长字段，用于存储消息的具体数据内容。
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcMessage {
    /**
     * rpc message type
     */
    private byte messageType;
    /**
     * serialization type
     */
    private byte codec;
    /**
     * request id
     */
    private int requestId;
    /**
     * request data
     */
    private Object data;
}
