package com.ibm.rpc_starter.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/16:59
 * @Description: 自定义编码器，将rpc返回结果编码为字节流
 */
@Slf4j
public class RPCEncoder extends MessageToByteEncoder {
    private Serializer serializer;
    public RPCEncoder(Serializer serializer)
    {
        this.serializer=serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] data = serializer.serialize(o);
        if (data == null) {
            log.error("encode fail,result data null,result object=" + o);
        }
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
