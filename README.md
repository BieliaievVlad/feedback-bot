# Feedback Bot

Telegram bot for collecting and analyzing anonymous employee feedback.

## Description

This project allows employees to submit feedback via Telegram. The feedback is analyzed using OpenAI, stored in Google Docs, and Trello. 
An admin panel is provided to view and filter all feedbacks.

---

## Features

- Send feedback via Telegram Bot
- Analyze feedback with OpenAI API
- Store feedback in Google Docs
- Track feedback in Trello
- Admin panel with filtering by date, branch, position, category, and level
- Spring Security-based authentication

---

## Tech Stack

- **Spring Boot 3.5.6** 
- **Spring Web**
- **Spring Data JPA** 
- **PostgreSQL** 
- **Flyway**
- **Spring Security** 
- **Thymeleaf**
- **Telegram Bot API**
- **OpenAI API**
- **Google Docs API**
- **Trello API**
- **JUnit 5 + Mockito**

---

## Configuration

Create `.env` file with the following keys:

```properties
TELEGRAM_BOT_TOKEN=YOUR_TELEGRAM_BOT_TOKEN
TELEGRAM_BOT_USERNAME=YOUR_TELEGRAM_BOT_USERNAME
TELEGRAM_WEBHOOK_PATH=/YOUR_TELEGRAM_WEBHOOK_PATH

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/YOUR_DB_NAME
SPRING_DATASOURCE_USERNAME=YOUR_DB_USERNAME
SPRING_DATASOURCE_PASSWORD=YOUR_DB_PASSWORD

SPRING_FLYWAY_URL=jdbc:postgresql://localhost:5432/YOUR_DB_NAME
SPRING_FLYWAY_USER=YOUR_DB_USERNAME
SPRING_FLYWAY_PASSWORD=YOUR_DB_PASSWORD

HIBERNATE_DEFAULT_SCHEMA=YOUR_SCHEMA_NAME

GOOGLE_CREDS_PATH=/path/to/your/google-credentials.json
GOOGLE_DOCUMENT_ID=YOUR_GOOGLE_DOCUMENT_ID

OPEN_API_KEY=YOUR_OPENAI_API_KEY

TRELLO_API_KEY=YOUR_TRELLO_API_KEY
TRELLO_API_TOKEN=YOUR_TRELLO_API_TOKEN
TRELLO_LIST_ID=YOUR_TRELLO_LIST_ID
```

---

## Running the Application

- Create database
- Activate the webhook 

For example:

```
https://api.telegram.org/botYOUR_BOT_TOKEN/setWebhook?url=YOUR_URL/YOUR_TELEGRAM_WEBHOOK_PATH
```

- Run the application using the following commands:

```bash
mvn clean package
java -jar target/feedback-bot-0.0.1-SNAPSHOT.jar
```

- Admin panel address (default):

```
http://localhost:8080
```

Default pre-installed user:

Login:```admin```

Password:```admin```
