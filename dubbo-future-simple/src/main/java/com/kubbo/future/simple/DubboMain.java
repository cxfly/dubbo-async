package com.kubbo.future.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <title>DubboMain</title>
 * <p></p>
 * Copyright © 2013 Phoenix New Media Limited All Rights Reserved.
 *
 * @author zhuwei
 *         14-8-21
 */
public class DubboMain {

    private static final Logger logger = LoggerFactory.getLogger(DubboMain.class);

    public static void main(String[] args) {
        String xml = "applicationContext-flow-" + args[0] + ".xml";


        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xml);

        context.start();

        logger.info(xml + " started");
        synchronized (DubboMain.class) {
            try {
                DubboMain.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}