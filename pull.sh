#!/bin/bash

. ./env.sh

docker pull ${REGISTRY}/${PROJ_NAME}:${VERSION}
