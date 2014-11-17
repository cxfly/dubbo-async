package com.kubbo.future.simple;

import com.kubbo.future.simple.api.S1;
import com.kubbo.future.simple.api.S2;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangyou on 14-11-17.
 */
public class S2Test extends AbstractTest {



    private S2 s2;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.s2 = (S2) super.context.getBean("s2");
    }


    @Test
    public void testS2() throws Exception {

        Future<String> future = this.s2.s2("hello");
        String result = Await.result(future, Duration.create(10, TimeUnit.SECONDS));
        assertEquals("s2:hello", result);

    }




}
