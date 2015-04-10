#!/bin/bash

cd .
. props.cfg

kill -9 `cat pid`
rm -f adam.jar
mv *SNAPSHOT*.jar adam.jar

java -jar adam.jar --facebook.email=$fb_email --facebook.password=$fb_pass > out.log &
PID=$!
echo "$PID" > pid