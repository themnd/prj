#!/bin/bash

ARGS=$*
OPTS="-src=./src/scripts"
if [ "$ARGS" != "" ]; then
  OPTS="$OPTS $ARGS"
fi;

#pushd ../dbschema
#mvn clean package install
#popd
#mvn clean package exec:java -Dexec.mainClass="it.snova.testjpa.Main" -Dexec.args="$OPTS"
mvn clean package -Pexecute exec:java -Dexec.args="$OPTS"
