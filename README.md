# Kaiburr-task-1
Kaiburr-task1-restAPI
# Kaiburr Assessment - Task 1: Java REST API

This is a Spring Boot application that provides a REST API for creating, finding, deleting, and executing "task" objects. The tasks are stored in a MongoDB database.

---

## How to Run the Application

### Prerequisites
* Java JDK 21 (or 17+)
* MongoDB
* Apache Maven (or use the included Maven wrapper)

### Steps
1.  **Start MongoDB:** Ensure your MongoDB server is running on its default port (`27017`).
2.  **Run the Application:** Open the project in an IDE (like VS Code or IntelliJ) and run the `TaskapiApplication.java` file.
3.  The API will be running at `http://localhost:8081`.

---

## [cite_start]API Endpoints (How to Use) 

You can use a tool like Postman to test the API.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `PUT` | `http://localhost:8081/tasks` | Creates a new task. |
| `GET` | `http://localhost:8081/tasks` | Gets a list of all tasks. |
| `GET` | `http://localhost:8081/tasks/findByName?name={name}` | Finds tasks that contain the search string. |
| `PUT` | `http://localhost:8081/tasks/{id}/execute` | Executes the command for a specific task. |
| `DELETE` | `http://localhost:8081/tasks/{id}` | Deletes a task by its ID. |

---

## [cite_start]Screenshots of API Tests [cite: 23]

[cite_start]**Note:** All screenshots include the current date/time and my name (visible in a text editor or terminal) as required[cite: 8].

### 1. Create Task (PUT /tasks)
<img width="1918" height="1078" alt="Task 1 - 1" src="https://github.com/user-attachments/assets/1b527664-99c2-4e7b-9b33-7253ed7ffe0c" />

### 2. Execute Task (PUT /tasks/{id}/execute)
<img width="1918" height="1078" alt="TAsk 1 - 2" src="https://github.com/user-attachments/assets/548c75d0-f7a9-4565-8a5b-3cc0acf77bed" />

### 3. Get All Tasks (GET /tasks)
<img width="1918" height="1078" alt="3 1" src="https://github.com/user-attachments/assets/1ff63e94-5cab-41bd-a865-9c5e60eac641" />
<img width="1918" height="1078" alt="3" src="https://github.com/user-attachments/assets/36382699-fb44-451b-acbd-0b955a4731c5" />

### 4. Find By Name (GET /tasks/findByName)
<img width="1918" height="1078" alt="4" src="https://github.com/user-attachments/assets/732b8eb9-ef3f-424b-878e-1a95b460d409" />

### 5. Delete Task (DELETE /tasks/{id})
<img width="1918" height="1078" alt="5" src="https://github.com/user-attachments/assets/63e47237-9fe6-44db-8e84-d6cc77fa97b2" />
