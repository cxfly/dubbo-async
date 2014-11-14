package com.alibaba.dubbo.examples.async2.api;

import scala.concurrent.Future;
import scala.util.Success;

/**
 * Created by jiangyou on 14-11-13.
 */
public interface Echo {


    public String syncEcho(String text);

    public Future<String> asyncEcho(String text);

}
