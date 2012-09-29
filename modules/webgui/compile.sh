#!/bin/bash

pushd ../..
mvn install
popd
cp target/*war ~/dev/tomcat/webapps/
