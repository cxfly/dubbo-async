package com.kubbo.future.simple.impl;

import akka.dispatch.Futures;
import com.kubbo.future.simple.api.Echo;
import scala.concurrent.Future;

import javax.management.RuntimeErrorException;

/**
 * Created by jiangyou on 14-11-17.
 */
public class EchoImpl implements Echo {
    public String syncEcho(String text) {
        return text;
    }

    public String syncEchoWithError(String text) {
        throw new RuntimeException();
    }

    public Future<String> asyncEcho(String text) {
        return Futures.successful(text);
    }

    public Future<String> asyncEchoWithError(String text) {
        return Futures.failed(new RuntimeException());
    }
}