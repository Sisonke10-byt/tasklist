#!/bin/bash
set -e

git add .


git commit -m "Configure application.properties for PostgreSQL connection"
git commit -m "Add Docker Compose setup for PostgreSQL database"
git commit -m "Implement successful Swagger API testing verification"
git commit -m "Document tested endpoints and usage with Postman & Swagger"


git push origin dev