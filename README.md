
Dubbo-future is async rpc framework based on dubbo.The most important future is that the return type of an interface method support [`scala.concurrent.Future`](http://www.scala-lang.org/api/2.10.2/#scala.concurrent.Future).
It's like twitter's [finagle](https://twitter.github.io/finagle),but it compared with the former more simple


##scene

There are 3 service,s1,s2,s3

s2 is dependent on s1

s3 is dependent on s2

    time
    |       consumer                                                                        |        
    |         | ------->rpc call(block)                                                     |
    |         |                                                                             |
    |         s3                                                        |                   |
    |         |-----                                                    |                   |c
    |               |  ----->rpc call(block)                            |                   |o
    |               |                                                   |                   |n
                    s2                                                  |s3                 |s
                    |---                            |s2                 |b                  |u
                        |                           |b                  |l                  |m
                        |---rpc call(block)         |l                  |o                  |e
                        s1                          |o                  |c                  |r
                        |                           |c                  |k                  |
                        |                           |k                  |i                  |b
                        do th                       |                   |n                  |l
                    |---                                                |g                  |o
    |               |                                                   |                   |c
                    |                                                   |                   |k
                    do sth                                              |                   |i
    |         ------                                                    |                   |n
    |         |                                                                             |g
    |         to sth                                                                        |
    |         |                                                                             |
    |        return                                                                         |

When a consumer call s3,the consumer's thread is blocking before the response of s2 and s2 is waiting the response of s1
* consumer thread is blocking for s3 return
* s3 thread is blocking for s2 return
* s2 thread is blocking for s1 return

What is worse,if one of the services is slower,the front caller will be blocking very long time
that will be terrible and waste of resources.

It is is very bad for throughput,so this fork is solving this situation



##Futures

* support return type:[`scala.concurrent.Future`](http://www.scala-lang.org/api/2.10.2/#scala.concurrent.Future)
* all rpc call done by callback and avoid blocking

##Build
* Latest stable Oracle JDK 6
* Latest stable Apache Maven

##Demo
api:
```java
public interface Test {
    public String syncTest(String text);
    public Future<String> asyncTest(String text);
}
```

s1
```java
public interface Test1 extends Test{
}
```
s2
```java
public interface Test2 extends Test {
}
```
s3
```java
public interface Test3 extends Test{
}
```

impl:
s1Impl
```java
public class Test1Impl implements Test1 {
    @Override
    public String syncTest(String str) {
        return "test1_sync:" + str;
    }
    @Override
    public Future<String> asyncTest(String str) {
        return Futures.successful("test1_async:" + str);
    }
}
```

s2Impl
```java
public class Test2Impl implements Test2 {
    private Test1 test1;
    public Test1 getTest1() {
        return test1;
    }
    public void setTest1(Test1 test1) {
        this.test1 = test1;
    }
    @Override
    public String syncTest(String str) {
        return test1.syncTest("test2_sync:" + str);
    }
    @Override
    public Future<String> asyncTest(String text) {
        return test1.asyncTest("test2_async:" + text);
    }
}
```
s3Impl
```java
public class Test3Impl implements Test3 {
    private Test2 test2;
    public Test2 getTest2() {
        return test2;
    }
    public void setTest2(Test2 test2) {
        this.test2 = test2;
    }
    @Override
    public String syncTest(String str) {
        return test2.syncTest("test3_sync:" + str);
    }
    @Override
    public Future<String> asyncTest(String text) {
        return test2.asyncTest("test3_async:" + text);
    }
}
```
test:

```java

 Future<String> future = this.s3.asyncTest("hello");
        String result = Await.result(future, Duration.create(10, TimeUnit.SECONDS));
        System.out.println(result)
```
##Benchmark
proxy:一个http-server,convert http request to rpc call

test1:two method:syncTest,AsyncTest

test2:two method:syncTest,AsyncTest

test3:two method:syncTest,AsyncTest

all service is deployed in different server

bench result:

service  | sync         |async
---------|--------------|----------
proxy->s1| 1.1w         |1.3w
proxy->s2| 5K           |9K
proxy->s3| 3.5-4k       |8K

According to the above,the performance of async is twice as large as sync

##Question
Is there any problem in use, welcome back to me, you can communicate with me with the following contact

* mail(akkako#126.com, 把#换成@)
