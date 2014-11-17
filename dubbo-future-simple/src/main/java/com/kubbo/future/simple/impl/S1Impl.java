package com.kubbo.future.simple.impl;

import akka.dispatch.Futures;
import com.kubbo.future.simple.api.S1;
import scala.concurrent.Future;

/**
 * Created by jiangyou on 14-11-17.
 */
public class S1Impl implements S1 {

    public Future<String> s1(String text) {
        return Futures.successful("s1:" + text);
    }
}
