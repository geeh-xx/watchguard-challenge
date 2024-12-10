# WATCHGUARD

# CHALLENGE DESCRIPTION 

Develop a simple application using Spring Boot.
It will read messages from Kafka and save them to disk, grouped in gzip files.
The data dump will be performed when a number of messages is reached or after a time interval indicated in the
configuration file.
The content of the messages will be of String type. When writing them to disk they will be concatenated separated with
carriage returns.
The parameters of connection to Kafka, the Kafka Topic to read and the destination path of the gzip files will be obtained
from the configuration file.

## Tech Stack 

- Docker -> Build Docker image for the app and dependencies
- SpringBoot -> framework for java to use iac and manage dependencies
- Java -> language for development

## Project Structure

This project uses the clean arch to organize packages:

```
.
├── src/
|   ├── infrastructure/ - package for configuration 
│   ├── provider/ - package for external providers
│   ├── service/ - packages for the business           
│   ├── test/ - unit test files
```
## Key files

- `docker-compose.yml`: the app depencies and config the networks.
- `Dockerfile`: Contains instructions for building the Docker image for the java app.

## Prerequisites

- Docker

# How to Run 

Follow these steps to set up and run the project:

1. Run Docker Compose to start the services:

```
docker-compose up 
```

This command will build the necessary Docker images and start the containers with the depencies.

2. After you run successfully, have the containers.

1. `zookeeper-watchguard`: zookeeper.
2. `kafka-watchguard`: kafka for topics.
