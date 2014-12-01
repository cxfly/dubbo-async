package com.kubbo.future.simple.impl;


import akka.dispatch.Futures;
import com.kubbo.future.simple.api.Test2;
import com.kubbo.future.simple.api.Test3;
import scala.concurrent.Future;

/**
 * <title>Test3Impl</title>
 * <p></p>
 * Copyright © 2013 Phoenix New Media Limited All Rights Reserved.
 *
 * @author zhuwei
 *         14-8-21
 */
public class Test3Impl implements Test3 {



    private Test2 test2;

    public Test2 getTest2() {
        return test2;
    }

    public void setTest2(Test2 test2) {
        this.test2 = test2;
    }

    @Override
    public String syncTest(String str) {
        return test2.syncTest("test3_sync:" + str);
    }

    @Override
    public Future<String> asyncTest(String text) {
        return test2.asyncTest("test3_async:" + text);
    }

    @Override
    public Future<String> test3(String text) {
        return Futures.successful("test3:" + text);
    }
}