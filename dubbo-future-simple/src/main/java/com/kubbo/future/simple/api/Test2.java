package com.kubbo.future.simple.api;

import scala.concurrent.Future;

/**
 * <title>Test2</title>
 * <p></p>
 * Copyright © 2013 Phoenix New Media Limited All Rights Reserved.
 *
 * @author zhuwei
 *         14-8-21
 */
public interface Test2 extends Test {

    public Future<String> test2(String text);

}