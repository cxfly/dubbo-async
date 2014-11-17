import akka.actor.ActorSystem;
import akka.dispatch.OnComplete;
import junit.framework.TestCase;
import org.junit.Test;
import scala.concurrent.ExecutionContext;
import scala.concurrent.impl.Promise;

import java.util.concurrent.Executors;

/**
 * Created by jiangyou on 14-11-14.
 */
public class FutureTest extends TestCase {


    @Test
    public void testFuture() throws InterruptedException {

         final ExecutionContext ctx = ActorSystem.create("future-system").dispatcher();
        Promise.DefaultPromise promise = new Promise.DefaultPromise();
        promise.success("hello");
        promise.onComplete(new OnComplete<Object>() {
            @Override
            public void onComplete(Throwable failure, Object success) throws Throwable {
                System.out.println(success);

            }
        },ctx);
        promise.onComplete(new OnComplete<Object>() {
            @Override
            public void onComplete(Throwable failure, Object success) throws Throwable {
                System.out.println(success);

            }
        },ctx);



        Thread.sleep(3000);
    }
}
