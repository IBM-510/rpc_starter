package com.ibm.rpc_starter.consumer;

import com.ibm.rpc_starter.common.util.ServiceUtils;
import com.ibm.rpc_starter.model.SimpleRpcRequest;
import com.ibm.rpc_starter.model.SimpleRpcResponse;
import com.ibm.rpc_starter.registry.ServiceRegistry;
import com.ibm.rpc_starter.registry.model.ServiceMetaConfig;
import com.ibm.rpc_starter.serialize.RPCConsumerDecoder;
import com.ibm.rpc_starter.serialize.RPCConsumerEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/05/24/13:59
 * @Description: consumer netty handler
 */
@Slf4j
public class SimpleRpcConsumerNettyHandler extends SimpleChannelInboundHandler<SimpleRpcResponse> {

    /**
     * 注册中心
     */
    private ServiceRegistry serviceRegistry;

    /**
     * netty EventLoopGroup
     */
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);

    /**
     * netty channel
     */
    private Channel channel;

    /**
     * rpc response
     */
    private SimpleRpcResponse rpcResponse;

    /**
     * lock
     */
    private final Object lock = new Object();
    private RPCConsumerDecoder rpcDecoder;
    private RPCConsumerEncoder rpcEncoder;

    public SimpleRpcConsumerNettyHandler(ServiceRegistry serviceRegistry, RPCConsumerDecoder rpcDecoder, RPCConsumerEncoder rpcEncoder) {
        this.serviceRegistry = serviceRegistry;
        this.rpcDecoder=new RPCConsumerDecoder(rpcDecoder);
        this.rpcEncoder=new RPCConsumerEncoder(rpcEncoder);
    }

    /**
     * 发起RPC网络调用请求
     *
     * @param simpleRpcRequest 请求参数
     * @return
     */
    public SimpleRpcResponse sendRpcRequest(SimpleRpcRequest simpleRpcRequest) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("rpcEncoder", rpcEncoder);
                            pipeline.addLast("rpcDecoder", rpcDecoder);
                            socketChannel.pipeline()
                                    //通过.class获取此类型的实例（https://www.cnblogs.com/penglee/p/3993033.html）
                                    .addLast(SimpleRpcConsumerNettyHandler.this);
                        }
                    });

            String key = ServiceUtils.buildServiceKey(simpleRpcRequest.getClassName(), simpleRpcRequest.getServiceVersion());
            ServiceMetaConfig serviceMetaConfig = this.serviceRegistry.discovery(key);
            if (serviceMetaConfig == null) {
                log.error("sendRpcRequest fail,serviceMetaConfig not found");
                throw new Exception("serviceMetaConfig not found in registry");
            }

            log.info("sendRpcRequest begin,serviceMetaConfig=" + serviceMetaConfig.toString() + ",key=" + key);
            final ChannelFuture channelFuture = bootstrap.connect(serviceMetaConfig.getAddress(), serviceMetaConfig.getPort())
                    .sync();
            channelFuture.addListener((ChannelFutureListener)args0 -> {
                if (channelFuture.isSuccess()) {
                    log.info("rpc invoke success,");
                } else {
                    log.info("rpc invoke fail," + channelFuture.cause().getStackTrace());
                    eventLoopGroup.shutdownGracefully();
                }
            });

            this.channel = channelFuture.channel();
            this.channel.writeAndFlush(simpleRpcRequest).sync();

            synchronized (this.lock) {
                log.info("sendRpcRequest lock.wait");
                this.lock.wait();
            }

            log.info("get rpc response=" + rpcResponse.toString());
            return this.rpcResponse;
        } catch (Exception e) {
            log.error("sendRpcRequest exception,", e);
            return null;
        } finally {
            //关闭相关连接
            if (this.channel != null) {
                this.channel.close();
            }
            if (this.eventLoopGroup != null) {
                this.eventLoopGroup.shutdownGracefully();
            }
        }
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SimpleRpcResponse simpleRpcResponse) throws Exception {
        this.rpcResponse = simpleRpcResponse;

        log.info("rpc consumer netty handler,channelRead0,rpcResponse=" + rpcResponse);

        //收到远程网络的rpc response，通知调用端
        synchronized (lock) {
            log.info("channelRead0 simpleRpcResponse lock.notifyAll");
            lock.notifyAll();
        }
    }
}
