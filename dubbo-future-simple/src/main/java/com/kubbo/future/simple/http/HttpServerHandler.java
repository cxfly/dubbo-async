package com.kubbo.future.simple.http;

import akka.dispatch.OnComplete;
import com.alibaba.dubbo.rpc.AsyncContext;

import com.kubbo.future.simple.api.Test;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import scala.concurrent.Future;

import java.util.List;

/**
 * Created by jiangyou on 14-11-18.
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-flow-consumer.xml");

    private static final Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);
    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            if (request.getDecoderResult().isFailure()) {
                send400(ctx);
                return;
            }


            QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
            String service = decoder.parameters().containsKey("service") ? decoder.parameters().get("service").get(0) : null;
            String text = decoder.parameters().get("text").get(0);
            String async = decoder.parameters().get("async").get(0);
            //get large data from post
            if(request.getMethod() == HttpMethod.POST){
                HttpPostRequestDecoder postDecoder = new HttpPostRequestDecoder(request);
                try {
                    InterfaceHttpData httpData = postDecoder.getBodyHttpData("text");
                    if (httpData != null && httpData.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                        text = ((Attribute) httpData).getValue();
                    }
                }catch(HttpPostRequestDecoder.EndOfDataDecoderException e){
                }

            }
            if(service == null){
                send200(ctx,text);
                return;
            }
            Test test = (Test) context.getBean(service);
            if ("true".equals(async)) {
                Future<String> future = test.asyncTest(text);
                future.onComplete(new OnComplete<String>() {
                    @Override
                    public void onComplete(Throwable failure, String success) throws Throwable {
                        if (failure != null) {
                            logger.error(failure.getMessage(), failure);
                            send500(ctx);

                        }else {
                            send200(ctx, success);
                        }
                                            }
                }, AsyncContext.context());


            }else {
                String result = test.syncTest(text);
                send200(ctx, result);
            }

        }
    }




    private void send400(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


    private void send500(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void send200(ChannelHandlerContext ctx, Object content) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(content.toString().getBytes()));

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ReadTimeoutException) {
            logger.info("readTimeout");

        } else {
            logger.error(cause.getMessage(), cause);
        }


        send500(ctx);

    }
}
