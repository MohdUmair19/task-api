# Task API

A simple Java Spring Boot REST API to manage "tasks" which represent shell commands.  
Tasks are stored in MongoDB and can be created, searched, deleted, and executed.

## Features
- Create, update, delete tasks
- Search tasks by name or get by ID
- Execute task commands and store execution results
- Stored in MongoDB

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/tasks` | Get all tasks |
| GET | `/tasks?id={id}` | Get task by ID |
| PUT | `/tasks` | Create or update a task |
| DELETE | `/tasks/{id}` | Delete a task |
| GET | `/tasks/search?name={name}` | Find tasks by name |
| PUT | `/tasks/{id}/execute` | Execute a task command |

## Sample Task JSON
```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!"
}
```

## MongoDB Usage
- Database: `taskdb`
- Collection: `tasks`
- TaskExecutions stored inside each task in `taskExecutions` array

**Check data using Mongosh:**
```js
use taskdb
show collections
db.tasks.find().pretty()
```

## Running the Application

1. Ensure MongoDB is running:

**Windows:**
```powershell
mkdir C:\data\db
mongod --dbpath C:\data\db
```

**Linux/macOS:**
```bash
sudo mkdir -p /data/db
sudo chown $(whoami) /data/db
mongod --dbpath /data/db
```

2. Run the Spring Boot application:

**Using Maven:**
```bash
mvn spring-boot:run
```

**Or build and run JAR:**
```bash
mvn clean package
java -jar target/task-api-1.0.0.jar
```

3. Test the API using curl or Postman with the endpoints above.
