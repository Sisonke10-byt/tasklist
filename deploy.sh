#!/bin/bash
# deploy.sh - deploys the Tasklist app using Docker Compose (no git pull)

# Navigate to the app directory
cd ~/tasklist || exit 1

# Build or rebuild Docker images
docker-compose build

# Stop any existing containers
docker-compose down

# Start containers in detached mode
docker-compose up -d

# Optional: show running containers
docker ps
