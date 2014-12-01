import akka.dispatch.Mapper;
import akka.dispatch.OnComplete;
import com.alibaba.dubbo.rpc.AsyncContext;
import com.kubbo.future.simple.api.Test1;
import com.kubbo.future.simple.api.Test2;
import com.kubbo.future.simple.api.Test3;
import com.kubbo.future.simple.impl.Test1Impl;
import com.kubbo.future.simple.impl.Test2Impl;
import com.kubbo.future.simple.impl.Test3Impl;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhu on 2014/12/1.
 */
public class TestImplTest extends TestCase {


    private Test1Impl test1 = new Test1Impl();
    private Test2Impl test2 = new Test2Impl();
    private Test3Impl test3 = new Test3Impl();
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }


    /**
     * 场景：test1的返回值为test2的参数
     *      test2的返回值为test3的参数
     * @throws Exception
     */
    @Test
    public void testTest1() throws Exception {
        Future<String> future = test1.test1("hello world");
        future = future.flatMap(new Mapper<String, Future<String>>() {
            @Override
            public Future<String> apply(String parameter) {
                Future<String> future = test2.test2(parameter);
                return future.flatMap(new Mapper<String, Future<String>>() {
                    @Override
                    public Future<String> apply(String parameter) {
                        return test3.test3(parameter);
                    }
                }, AsyncContext.context());
            }
        }, AsyncContext.context());
        String result = Await.result(future, Duration.create(10, TimeUnit.SECONDS));
        Assert.assertEquals("test3:test2:test1:hello world", result);
    }

}
