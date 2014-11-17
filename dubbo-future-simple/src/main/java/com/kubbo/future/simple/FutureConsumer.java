/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kubbo.future.simple;


import akka.dispatch.OnFailure;
import com.alibaba.dubbo.rpc.AsyncContext;
import com.kubbo.future.simple.api.Echo;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import scala.Function1;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.util.concurrent.TimeUnit;


/**
 * CallbackConsumer
 *
 * @author william.liangf
 */
public class FutureConsumer {

    public static void main(String[] args) throws Exception {
        String config = FutureConsumer.class.getPackage().getName().replace('.', '/') + "/future-consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();


        final Echo echo = (Echo) context.getBean("echoService");
//        //warn
//        System.out.println(echo.syncEcho("hello world"));
//        System.out.println(echo.syncEcho("hello world"));
//        System.out.println(echo.syncEcho("hello world"));
//
//        Future<String> future = echo.asyncEcho("async hello world");
//        System.out.println(Await.result(future, Duration.create(10, TimeUnit.DAYS)));
//
//        future = echo.asyncEcho("async hello world");
//        System.out.println(Await.result(future, Duration.create(10, TimeUnit.DAYS)));
//        future = echo.asyncEcho("async hello world");
//        System.out.println(Await.result(future, Duration.create(10, TimeUnit.DAYS)));
//
//        long start = System.currentTimeMillis();
//        System.out.println(echo.syncEcho("hello world"));
//        long mid = System.currentTimeMillis();
//        future = echo.asyncEcho("async hello world");
//        System.out.println(Await.result(future, Duration.create(10, TimeUnit.DAYS)));
//        long end = System.currentTimeMillis();
//
//        System.out.println("sync:" + (mid - start));
//        System.out.println("async:" + (end - mid));
//
//
//        try {
//            echo.syncEchoWithError("hello world");
//        } catch (Exception e) {
//            System.out.println(e);
//        }


        Future future = echo.asyncEchoWithError("hello world");
        future.onFailure(new OnFailure() {
            @Override
            public void onFailure(Throwable failure) throws Throwable {
                System.out.println("OnFailure:");
            }
        }, AsyncContext.context());

        Thread.sleep(3000);
        System.exit(0);

    }

}
