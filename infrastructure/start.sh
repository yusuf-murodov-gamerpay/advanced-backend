#!/bin/bash

DOCKER_COMPOSE_FILE="docker-compose.yml"
ENV_FILE=".config/.env"

check_docker_installed() {
    if ! [ -x "$(command -v docker-compose)" ]; then
        echo "Error: docker-compose is not installed. Please install it before running this script."
        exit 1
    fi
}

start_project() {
    echo "Starting up the project..."
    echo "Using compose file: $DOCKER_COMPOSE_FILE"
    echo "Using environment file: $ENV_FILE"
    docker-compose -f $DOCKER_COMPOSE_FILE --env-file $ENV_FILE up -d

    if [ $? -eq 0 ]; then
        echo "✅ Project started successfully!"
    else
        echo "❌ Failed to start the project. Check the logs for details."
    fi
}

check_docker_installed
start_project