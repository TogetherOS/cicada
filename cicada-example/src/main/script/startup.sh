#!/usr/bin/env bash

ID=`ps -ef | grep "cicada-example" | grep -v "grep" |grep -v "startup"| awk '{print $2}'`
for id in $ID
do
kill -9 $id
echo "killed $id"
done

nohup java -jar cicada-example.jar > log.log 2>&1 &