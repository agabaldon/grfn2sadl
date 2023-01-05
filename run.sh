#!/bin/bash
. ./env.sh
echo "docker run -d --name=${PROJ_NAME} -p ${PORT}:10800 ${REGISTRY}/${PROJ_NAME}:${VERSION}"
docker run -d --name=${PROJ_NAME} -p ${PORT}:10800 ${REGISTRY}/${PROJ_NAME}:${VERSION}
