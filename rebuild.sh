#!/bin/bash -e

cd "$(dirname $0)"

echo "sbt clean test package dist-tgz $@"
sbt clean test package dist-tgz $@
