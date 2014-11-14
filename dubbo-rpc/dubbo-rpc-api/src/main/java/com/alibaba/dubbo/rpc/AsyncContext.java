package com.alibaba.dubbo.rpc;

import akka.actor.ActorSystem;
import akka.dispatch.ExecutionContexts;
import scala.concurrent.ExecutionContext;

import java.util.concurrent.Executors;

/**
 * Created by jiangyou on 14-11-14.
 */
public abstract class AsyncContext {
    private static ExecutionContext ctx = ExecutionContexts.fromExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
    public static ExecutionContext context() {
        return ctx;
    }
}
