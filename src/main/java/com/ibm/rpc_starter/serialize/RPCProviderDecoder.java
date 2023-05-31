package com.ibm.rpc_starter.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/15:44
 * @Description: 服务端解码器，对rpc请求进行解码
 */
@Slf4j
public class RPCProviderDecoder extends ByteToMessageDecoder {
    private List<Serializer> serializer;
    private Class<?> genericClass;
    /**
     * Netty传输的消息长度也就是对象序列化后对应的字节数组的大小，存储在 ByteBuf 头部
     */
    private static final int BODY_LENGTH = 4;
    public RPCProviderDecoder(List<Serializer> serializer, Class<?> genericClass)
    {
        this.serializer=serializer;
        this.genericClass=genericClass;
    }
    public RPCProviderDecoder(RPCProviderDecoder other) {
        this.serializer = other.serializer;
        this.genericClass = other.genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //入参校验
        if (byteBuf.readableBytes() < BODY_LENGTH) {
            log.error("decode fail,input ByteBuf illegal,in.readableBytes=" + byteBuf.readableBytes());
            return;
        }
        byteBuf.markReaderIndex();
        //读取长度内容
        int dataLen = byteBuf.readInt();
        //剩余可读内容小于预定长度
        if (byteBuf.readableBytes() < dataLen) {
            log.error("decode fail,input ByteBuf illegal,in.readableBytes {0} less than dataLen {1}", byteBuf.readableBytes(), dataLen);
            return;
        }
        //读取序列化类型
        int serializeByte=byteBuf.readInt();
        Serializer serializer1=serializer.get(serializeByte);
        //读取实际内容
        byte[] actualDataBytes = new byte[dataLen-Integer.BYTES];
        byteBuf.readBytes(actualDataBytes);
        //反序列化
        Object dataObj = serializer1.deserialize(actualDataBytes,genericClass);
        if (dataObj == null) {
            log.error("decode fail,input ByteBuf illegal,dataObj null,actualDataBytes={0}", actualDataBytes);
            return;
        }
        list.add(dataObj);
    }
}
