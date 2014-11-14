package com.alibaba.dubbo.examples.async2.impl;

import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.dispatch.OnComplete;
import com.alibaba.dubbo.examples.async2.api.Echo;
import com.alibaba.dubbo.rpc.AsyncContext;
import scala.concurrent.Future;
import scala.util.Success;
import scala.util.Try;

import static com.alibaba.dubbo.rpc.AsyncContext.context;

/**
 * Created by jiangyou on 14-11-13.
 */
public class EchoImpl implements Echo {
    public String syncEcho(String text) {
        System.out.println("syncEcho");
        return text;
    }
    public Future<String> asyncEcho(String text) {
        System.out.println("asyncEcho");
        Future<String> future = Futures.successful(text);
        future.onComplete(new OnComplete<String>() {
            @Override
            public void onComplete(Throwable failure, String success) throws Throwable {
                System.out.println("hello ");
            }
        }, context());

        return future.flatMap(new Mapper<String, Future<String>>() {
            @Override
            public Future<String> apply(String parameter) {
                return Futures.successful(parameter + ":flat");
            }
        }, context());

    }
}
