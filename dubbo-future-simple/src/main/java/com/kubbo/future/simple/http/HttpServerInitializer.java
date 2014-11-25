package com.kubbo.future.simple.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by jiangyou on 14-11-18.
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final int _1M = 1024;



    @Override

    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast("codec", new HttpServerCodec());
        p.addLast("aggregator", new HttpObjectAggregator(10 * _1M));
        p.addLast("timeout", new ReadTimeoutHandler(10));
        p.addLast("handler", new HttpServerHandler());

    }
}
