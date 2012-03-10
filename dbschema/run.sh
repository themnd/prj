#!/bin/bash

ARGS=$*
OPTS="-src=./src/scripts"
if [ "$ARGS" != "" ]; then
  OPTS="$OPTS $ARGS"
fi;

mvn clean package exec:java -Dexec.mainClass="it.snova.dbmigrator.DBMigrator" -Dexec.args="$OPTS"
