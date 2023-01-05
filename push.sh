#!/bin/bash

. ./env.sh

docker push ${REGISTRY}/${PROJ_NAME}:${VERSION}
