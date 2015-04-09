#!/bin/bash

cd $1
. props.cfg

kill -9 `cat pid`
rm -f adam.jar
mv *SNAPSHOT*.jar adam.jar

java -jar adam.jar --facebook.email=$fb.email --facebook.password=$fb.pass > out.log &
PID=$!
echo "$PID" > pid
