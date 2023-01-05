#!/bin/bash

. ./env.sh

docker ps -a | grep ${PROJ_NAME}
