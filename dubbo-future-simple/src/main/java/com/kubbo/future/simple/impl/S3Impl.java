package com.kubbo.future.simple.impl;

import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.dispatch.OnSuccess;
import com.alibaba.dubbo.rpc.AsyncContext;
import com.kubbo.future.simple.api.S1;
import com.kubbo.future.simple.api.S2;
import com.kubbo.future.simple.api.S3;
import org.springframework.beans.factory.annotation.Autowired;
import scala.Function1;
import scala.concurrent.Future;
import scala.runtime.BoxedUnit;

/**
 * Created by jiangyou on 14-11-17.
 */
public class S3Impl implements S3 {


    private S1 s1;


    private S2 s2;


    public Future<String> s1(String text) {
        Future<String> future = s1.s1(text);
        return future;
    }

    public Future<String> s1s1(String text) {
        Future<String> future = s1.s1(text);
        return future.flatMap(new Mapper<String, Future<String>>() {
            @Override
            public Future<String> apply(String parameter) {
                return s2.s2(parameter);
            }
        }, AsyncContext.context());
    }

    public S1 getS1() {
        return s1;
    }

    public void setS1(S1 s1) {
        this.s1 = s1;
    }

    public S2 getS2() {
        return s2;
    }

    public void setS2(S2 s2) {
        this.s2 = s2;
    }
}
