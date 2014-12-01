package com.kubbo.future.simple.impl;

import akka.dispatch.Futures;
import com.kubbo.future.simple.api.Test1;
import scala.concurrent.Future;

/**
 * <title>Test1Impl</title>
 * <p></p>
 * Copyright © 2013 Phoenix New Media Limited All Rights Reserved.
 *
 * @author zhuwei
 *         14-8-21
 */
public class Test1Impl implements Test1 {


    @Override
    public String syncTest(String str) {
        return "test1_sync:" + str;
    }

    @Override
    public Future<String> asyncTest(String str) {
        return Futures.successful("test1_async:" + str);
    }

    @Override
    public Future<String> test1(String text) {
        return Futures.successful("test1:" + text);
    }
}