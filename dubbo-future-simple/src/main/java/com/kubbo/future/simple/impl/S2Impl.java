package com.kubbo.future.simple.impl;

import akka.dispatch.Futures;
import com.kubbo.future.simple.api.S1;
import com.kubbo.future.simple.api.S2;
import scala.concurrent.Future;

/**
 * Created by jiangyou on 14-11-17.
 */
public class S2Impl implements S2 {

    public Future<String> s2(String text) {
        return Futures.successful("s2:" + text);
    }
}
