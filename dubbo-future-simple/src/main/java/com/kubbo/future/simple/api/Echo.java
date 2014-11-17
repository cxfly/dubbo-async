package com.kubbo.future.simple.api;

import scala.concurrent.Future;

/**
 * Created by jiangyou on 14-11-17.
 */
public interface Echo {


    public String syncEcho(String text);


    public String syncEchoWithError(String text);


    public Future<String> asyncEcho(String text);


    public Future<String> asyncEchoWithError(String text);



}
