#!/bin/bash

. ./env.sh

echo "docker exec -it ${PROJ_NAME} /bin/bash"
docker exec -it ${PROJ_NAME} /bin/bash
