#!/bin/bash

rm -rf res

echo -e
hadoop com.sun.tools.javac.Main src/*.java
(cd src; jar cf res.jar *.class)
rm -rf src/*.class
mv src/res.jar ./res.jar

hadoop jar res.jar Main estudiants.txt matricules.txt res
