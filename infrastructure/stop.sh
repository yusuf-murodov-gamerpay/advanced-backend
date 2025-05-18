#!/bin/bash

DOCKER_COMPOSE_FILE="docker-compose.yml"
ENV_FILE=".config/.env"

check_docker_installed() {
    if ! [ -x "$(command -v docker-compose)" ]; then
        echo "Error: docker-compose is not installed. Please install it before running this script."
        exit 1
    fi
}

stop_project() {
    echo "Shutting down the project..."
    docker-compose -f $DOCKER_COMPOSE_FILE --env-file $ENV_FILE down

    if [ $? -eq 0 ]; then
        echo "✅ Project stopped successfully!"
    else
        echo "❌ Failed to stop the project. Check the logs for details."
    fi
}

check_docker_installed
stop_project