#!/bin/bash

APP_NAME=snowybizapp
IMAGE_NAME=${APP_NAME}:latest
CONTAINER_NAME=${APP_NAME}
PORT_MAPPING=9992:9102

start() {
    echo "Building the Docker image..."
    docker build -t $IMAGE_NAME .

    echo "Running the Docker container..."
    docker run -d -p $PORT_MAPPING --name $CONTAINER_NAME $IMAGE_NAME
}

stop() {
    echo "Stopping the Docker container..."
    docker stop $CONTAINER_NAME

    echo "Removing the Docker container..."
    docker rm $CONTAINER_NAME

    echo "Removing the Docker image..."
    docker rmi $IMAGE_NAME
}

restart() {
    stop
    start
}

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
        ;;
esac
