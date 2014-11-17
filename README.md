##dubbo+Future

there are 3 provider,s1,s2,s3

s1  is independent of s2


s3 is independent of s1,s2

    time
    |       consumer
    |         | ------->rpc call(block)
    |         |
              s3
              |--
                |  ----->rpc call(block)
                s1
               -|
              |
              |
              do something
              |
              |--
                |------->rpc call(block)
                s2
                |
              |-
              |
              return
    |
    |
    |
    |
when a consumer call s3,consumer should wait the response of s1 and s2 complete,the consumer thread is blocked,
This situation is very bad for throughput,so this fork is solving this situation





##Futures

* support return type:[`scala.concurrent.Future`](http://www.scala-lang.org/api/2.10.2/#scala.concurrent.Future)
* all rpc call done by callback and aviod blocking

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
##Benchmark

##Question
Is there any problem in use, welcome back to me, you can communicate with me with the following contact

* mail(akkako#126.com, 把#换成@)
