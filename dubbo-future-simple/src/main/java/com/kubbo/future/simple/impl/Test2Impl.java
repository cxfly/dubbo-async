package com.kubbo.future.simple.impl;


import akka.dispatch.Futures;
import com.kubbo.future.simple.api.Test1;
import com.kubbo.future.simple.api.Test2;
import scala.concurrent.Future;

/**
 * <title>Test2Impl</title>
 * <p></p>
 * Copyright © 2013 Phoenix New Media Limited All Rights Reserved.
 *
 * @author zhuwei
 *         14-8-21
 */
public class Test2Impl implements Test2 {


    private Test1 test1;

    public Test1 getTest1() {
        return test1;
    }

    public void setTest1(Test1 test1) {
        this.test1 = test1;
    }

    @Override
    public String syncTest(String str) {
        return test1.syncTest("test2_sync:" + str);
    }

    @Override
    public Future<String> asyncTest(String text) {
        return test1.asyncTest("test2_async:" + text);
    }

    @Override
    public Future<String> test2(String text) {
        return Futures.successful("test2:" + text);
    }
}