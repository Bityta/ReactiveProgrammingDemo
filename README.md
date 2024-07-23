# Project Name

## Prerequisites

- Docker
- Docker Compose

## Setup and Run

1. **Start Docker Compose:**

    ```bash
    docker-compose up
    ```

2. **Test Connection to RabbitMQ:**

    Run the `setup/TestConnection` class to verify the connection to RabbitMQ.

3. **Setup RabbitMQ:**

    Run the `setup/RabbitMqSetup` class to complete the RabbitMQ setup.

4. **Run Main Application:**

    Run the `Main` class to start the main application.

5. **Stop Containers:**

    ```bash
    docker-compose down
    ```
