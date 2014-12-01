package com.kubbo.future.simple.api;

import scala.concurrent.Future;

/**
 * Created by jiangyou on 14-8-21.
 */
public interface Test3 extends Test{

    public Future<String> test3(String text);

}
