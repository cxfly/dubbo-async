package com.kubbo.future.simple.api;

import scala.concurrent.Future;

/**
 * Created by jiangyou on 14-11-18.
 */
public interface Test {

    public String syncTest(String text);



    public Future<String> asyncTest(String text);

}
