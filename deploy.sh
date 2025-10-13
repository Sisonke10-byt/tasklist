#!/bin/bash
cd ~/tasklist
docker-compose -f docker-compose.ci.yml up -d --build
