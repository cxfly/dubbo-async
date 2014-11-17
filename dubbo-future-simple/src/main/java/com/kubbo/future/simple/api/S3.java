package com.kubbo.future.simple.api;

import scala.concurrent.Future;

/**
 * Created by jiangyou on 14-11-17.
 */
public interface S3 {
    public Future<String> s1(String text);


    public Future<String> s1s1(String text);
}
