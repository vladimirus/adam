#!/bin/bash
DIR=../target

kill -9 `cat ${DIR}/pid`

nohup java -jar ${DIR}/com.adam-0.0.1-SNAPSHOT.jar > ${DIR}/out.log &

PID=$!
echo "$PID" > ${DIR}/pid
