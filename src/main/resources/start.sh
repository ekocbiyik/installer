#!/bin/sh
LAUNCHER="$(dirname "$0")"/docker-desktop.jar
JAVA_PATH="jre"
java=java

if test -n "$JAVA_PATH"; then
    java="$JAVA_PATH/bin/java"
fi
exec "$java" $java_args -jar $LAUNCHER "$@"
exit 1
