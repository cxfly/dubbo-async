package com.kubbo.future.simple;

import com.alibaba.dubbo.rpc.filter.ExceptionFilter;
import com.kubbo.future.simple.api.Echo;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangyou on 14-11-17.
 */
public class EchoTest extends AbstractTest {


    private Echo echo;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.echo = (Echo) super.context.getBean("echoService");
    }

    @Test
    public void testSyncEcho() {

        String s = echo.syncEcho("hello sync");
        assertEquals("hello sync", s);
    }



    @Test
    public void testSyncEchoException() {
        Exception e = null;
        try {
            echo.syncEchoWithError("error hello");

        } catch (Exception e1) {
            e = e1;
        }

        assertEquals(RuntimeException.class, e.getClass());
    }


    @Test
    public void testAsyncEcho() throws Exception {
        Future<String> future = echo.asyncEcho("async hello");
        String result = Await.result(future, Duration.create(10, TimeUnit.SECONDS));
        assertEquals("async hello", result);
    }


    @Test
    public void testAsyncEchoError() throws Exception{
        Future<String> future = echo.asyncEchoWithError("async hello");
        Exception ex = null;
        try {
            Object result = Await.result(future, Duration.create(10, TimeUnit.SECONDS));
        } catch (Exception e) {
            ex = e;
        }

        assertEquals(RuntimeException.class, ex.getClass());


    }





}
