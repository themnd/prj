#!/bin/bash

pushd ~/dev/tomcat
rm -rf webapps/webgui
./bin/catalina.sh jpda run&
popd
