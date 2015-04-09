#!/bin/bash
cd $1

kill -9 `cat pid`
rm -f adam.jar
mv *SNAPSHOT*.jar adam.jar

nohup java -jar adam.jar --email=$2 --password=$3 > out.log &
PID=$!
echo "$PID" > pid
