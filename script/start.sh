#!/bin/bash

JVM_OPTS="-Xmx2g" # max heap size
JVM_OPTS="$JVM_OPTS -Xms2g" # init heap size
JVM_OPTS="$JVM_OPTS -XX:ParallelGCThreads=1" # single thread garbage collection
JVM_OPTS="$JVM_OPTS -XX:+UseG1GC" # use G1 garbage collector

# Don't add double quotes to $JVM_OPTS here to avoid parsing jvm parameters failing
java $JVM_OPTS -jar /iot/iot-device-web/lib/iot-device-web-*.jar
