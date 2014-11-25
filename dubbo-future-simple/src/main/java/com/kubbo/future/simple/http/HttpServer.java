package com.kubbo.future.simple.http;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by jiangyou on 14-11-18.
 */
public class HttpServer {


    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // Configure the server.
        Config config = ConfigFactory.load("http-server.conf");

        EventLoopGroup bossGroup = new NioEventLoopGroup(config.getInt("http-server.boss-number"));

        EventLoopGroup workerGroup = new NioEventLoopGroup(config.getInt("http-server.worker-number"));

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());

            Channel ch = b.bind(port).sync().channel();
            logger.info("HttpServer started");
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new HttpServer(port).run();
    }
}
