#!/bin/bash
. ./env.sh
echo "docker build -t ${REGISTRY}/${PROJ_NAME}:${VERSION} ."
docker build -t ${REGISTRY}/${PROJ_NAME}:${VERSION} .
