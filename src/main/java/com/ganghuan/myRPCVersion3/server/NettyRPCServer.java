package com.ganghuan.myRPCVersion3.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

/**
 * 实现RPCServer接口，负责监听与发送数据
 */
@AllArgsConstructor
public class NettyRPCServer implements RPCServer {
    private ServiceProvider serviceProvider;
    @Override
    public void start(int port) {
        // netty 服务线程组boss负责建立连接， work负责具体的请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();//用于处理服务器端接收客户端连接
        NioEventLoopGroup workGroup = new NioEventLoopGroup();//进行网络通信（读写）
        System.out.println("Netty服务端启动了...");
        try {
            // 启动netty服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();//辅助工具类，用于服务器通道的一系列配置
            // 初始化
            serverBootstrap.group(bossGroup,workGroup)//绑定两个线程组
                    .channel(NioServerSocketChannel.class)//指定NIO的模式
                    .childHandler(new NettyServerInitializer(serviceProvider));//配置具体的数据处理方式
            // 同步阻塞
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 死循环监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
    }
}
