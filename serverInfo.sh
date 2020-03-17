#/bin/bash

export APP_NAME=bella
export APP_URL=$(cf curl /v2/apps?q=name:$APP_NAME | grep \"url\" | awk '{ print $2 }' | cut -c2-46)
export APP_HOST=$(cf curl $APP_URL/stats | grep host | awk '{ print $2}' | cut -c 2-)
echo ${APP_HOST/%??/}
