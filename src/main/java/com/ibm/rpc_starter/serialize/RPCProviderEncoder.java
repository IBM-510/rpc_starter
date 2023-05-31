package com.ibm.rpc_starter.serialize;

import com.ibm.rpc_starter.model.SimpleRpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/16:59
 * @Description: 服务端自定义编码器，将rpc返回结果编码为字节流
 */
@Slf4j
public class RPCProviderEncoder extends MessageToByteEncoder<SimpleRpcResponse> {
    private List<Serializer> serializer;
    public RPCProviderEncoder(List<Serializer> serializer)
    {
        this.serializer=serializer;
    }

    public RPCProviderEncoder(RPCProviderEncoder other)
    {
        this.serializer=other.serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SimpleRpcResponse simpleRpcResponse, ByteBuf byteBuf) throws Exception {
        int serializerType=simpleRpcResponse.getSerializeType();
        Serializer serializer1=serializer.get(serializerType);
        byte[] data = serializer1.serialize(simpleRpcResponse);
        if (data == null) {
            log.error("encode fail,result data null,result object=" + simpleRpcResponse);
        }
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
