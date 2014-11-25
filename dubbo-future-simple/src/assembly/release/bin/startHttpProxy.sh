#!/bin/sh
 dir=$(dirname $0)/..
 for file in ${dir}/lib/*.jar
         do
CLASSPATH=$CLASSPATH:$file
done
CLASSPATH=$CLASSPATH:${dir}/conf

java -Xms512m -Xmx512m -Xmn128m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.log -Dwrite.statistics=true -cp $CLASSPATH "com.kubbo.future.simple.http.HttpServer" &