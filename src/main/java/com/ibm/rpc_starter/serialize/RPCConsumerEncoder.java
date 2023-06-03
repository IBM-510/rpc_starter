package com.ibm.rpc_starter.serialize;

import com.ibm.rpc_starter.common.enumeration.SerializeTypeEnum;
import com.ibm.rpc_starter.serialize.hessian.HessianSerializer;
import com.ibm.rpc_starter.serialize.kryo.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/23/16:59
 * @Description: 消费者自定义编码器，将rpc请求编码为字节流
 */
@Slf4j
public class RPCConsumerEncoder extends MessageToByteEncoder {
    public Serializer getSerializer() {
        return serializer;
    }

    private Serializer serializer;
    public RPCConsumerEncoder()
    {}
    public RPCConsumerEncoder(Serializer serializer)
    {
        this.serializer=serializer;
    }

    public RPCConsumerEncoder(RPCConsumerEncoder other)
    {
        this.serializer=other.serializer;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] data = serializer.serialize(o);
        if (data == null) {
            log.error("encode fail,result data null,result object=" + o);
        }
        byteBuf.writeInt(data.length+Byte.BYTES);//数据的字节数加上序列化协议的一个int
        if(serializer instanceof HessianSerializer)
            byteBuf.writeInt(SerializeTypeEnum.HESSIAN_TYPE.getType());
        else if(serializer instanceof KryoSerializer)
            byteBuf.writeInt(SerializeTypeEnum.KRYO_TYPE.getType());
        else
            log.error("the type of the serialize is unknown");
        byteBuf.writeBytes(data);
    }
}
