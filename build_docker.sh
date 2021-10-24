#!/bin/sh
if [ $# -eq 0 ]; then
  echo "No arguments supplied"
  exit 1
fi
mvn clean install
if [ $? -eq 1 ]; then
  exit 1
else
  docker build -t "shop:$1" .
fi

