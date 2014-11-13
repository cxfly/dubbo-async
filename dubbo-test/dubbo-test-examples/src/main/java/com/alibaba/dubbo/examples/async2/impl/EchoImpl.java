package com.alibaba.dubbo.examples.async2.impl;

import akka.dispatch.Futures;
import com.alibaba.dubbo.examples.async2.api.Echo;
import scala.concurrent.Future;

/**
 * Created by jiangyou on 14-11-13.
 */
public class EchoImpl implements Echo {
    public String syncEcho(String text) {
        return text;
    }

    public Future<String> asyncEcho(String text) {
        return Futures.successful(text);
    }
}
