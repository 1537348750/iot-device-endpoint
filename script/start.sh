#!/bin/bash

JAVA_OPTS="-Xmx2g" # max heap size
JAVA_OPTS="$JAVA_OPTS -Xms2g" # init heap size
JAVA_OPTS="$JAVA_OPTS -XX:ParallelGCThreads=1" # single thread garbage collection
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC" # use G1 garbage collector

echo "JAVA_OPTS: $JAVA_OPTS"

# Don't add double quotes to $JAVA_OPTS here to avoid parsing jvm parameters failing
java $JAVA_OPTS -jar /iot/iot-device-web/lib/iot-device-web-*.jar
