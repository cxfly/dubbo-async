package com.kubbo.future.simple;

import com.kubbo.future.simple.FutureConsumer;
import com.kubbo.future.simple.api.Echo;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangyou on 14-11-17.
 */
public class AbstractTest extends TestCase{



    protected ApplicationContext context;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String config = FutureConsumer.class.getPackage().getName().replace('.', '/') + "/future-consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();
        this.context = context;
    }


}
