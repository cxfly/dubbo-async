package com.kubbo.future.simple;

import com.kubbo.future.simple.api.S2;
import com.kubbo.future.simple.api.S3;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangyou on 14-11-17.
 */
public class S3Test extends AbstractTest {



    private S3 s3;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.s3 = (S3) super.context.getBean("s3");
    }


    @Test
    public void testS1() throws Exception {

        Future<String> future = this.s3.s1s1("hello");
        String result = Await.result(future, Duration.create(10, TimeUnit.SECONDS));
        assertEquals("s2:s1:hello", result);
    }




}
