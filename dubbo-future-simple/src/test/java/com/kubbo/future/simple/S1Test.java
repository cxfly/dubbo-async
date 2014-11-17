package com.kubbo.future.simple;

import com.kubbo.future.simple.api.S1;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangyou on 14-11-17.
 */
public class S1Test extends AbstractTest {



    private S1 s1;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.s1 = (S1) super.context.getBean("s1");
    }


    @Test
    public void testS1() throws Exception {

        Future<String> future = this.s1.s1("hello");

        String result = Await.result(future, Duration.create(10, TimeUnit.SECONDS));
        assertEquals("s1:hello", result);

    }




}
