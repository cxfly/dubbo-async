
Dubbo-future is async rpc framework based on dubbo.The most important future is that the return type of an interface method support [`scala.concurrent.Future`](http://www.scala-lang.org/api/2.10.2/#scala.concurrent.Future).
It's like twitter's [finagle](https://twitter.github.io/finagle),but it compared with the former more simple


##scene

There are 3 service,s1,s2,s3

s2 is independent on s1

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
s1
```java
public interface S1 {

    public Future<String> s1(String text);
}
```
s2

```java

public interface S2 {

    public Future<String> s2(String text);
}
```

s3
```java

public interface S3 {
    public Future<String> s1(String text);


    public Future<String> s1s1(String text);
}
```

impl:
s1Impl
```java

public class S1Impl implements S1 {

    public Future<String> s1(String text) {
        return Futures.successful("s1:" + text);
    }
}

```

s2Impl
```java
public class S2Impl implements S2 {

    public Future<String> s2(String text) {
        return Futures.successful("s2:" + text);
    }
}

```
s3Impl
```java
public class S3Impl implements S3 {


    private S1 s1;


    private S2 s2;


    public Future<String> s1(String text) {
        Future<String> future = s1.s1(text);
        return future;
    }

    public Future<String> s1s1(String text) {
        //thread no block,s1 return immediately
        Future<String> future = s1.s1(text);

        return future.flatMap(new Mapper<String, Future<String>>() {
            @Override
            public Future<String> apply(String parameter) {
                return s2.s2(parameter);
            }
        }, AsyncContext.context());
    }

    public S1 getS1() {
        return s1;
    }

    public void setS1(S1 s1) {
        this.s1 = s1;
    }

    public S2 getS2() {
        return s2;
    }

    public void setS2(S2 s2) {
        this.s2 = s2;
    }
}
```
test:

```java

 Future<String> future = this.s3.s1s1("hello");
        String result = Await.result(future, Duration.create(10, TimeUnit.SECONDS));
        assertEquals("s2:s1:hello", result);

```
##Benchmark

##Question
Is there any problem in use, welcome back to me, you can communicate with me with the following contact

* mail(akkako#126.com, 把#换成@)
