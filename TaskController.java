package com.example.taskapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks") // All URLs in this file start with /tasks
public class TaskController {

    // Spring will automatically give us an instance of the repository
    @Autowired 
    private TaskRepository taskRepository;

    // Endpoint: GET /tasks OR /tasks?id={id}
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Task> taskOpt = taskRepository.findById(id);
            if (taskOpt.isPresent()) {
                return ResponseEntity.ok(List.of(taskOpt.get()));
            } else {
                return ResponseEntity.notFound().build(); // 404
            }
        } else {
            return ResponseEntity.ok(taskRepository.findAll());
        }
    }

    // Endpoint: PUT /tasks (Create/Update a task)
    @PutMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // Simple security check [cite: 68]
        if (task.getCommand() == null || task.getCommand().contains("rm ") || task.getCommand().contains("sudo")) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        
        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(new ArrayList<>());
        }
        
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // Endpoint: DELETE /tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    // Endpoint: GET /tasks/findByName?name={name} [cite: 74]
    // GET /tasks/findByName?name={name}
    // GET /tasks/findByName?name={name}
    // GET /tasks/findByName?name={name}
    @GetMapping("/findByName")
    public ResponseEntity<List<Task>> findTasksByName(@RequestParam String name) {

        // This is the line we are testing again
        List<Task> tasks = taskRepository.findByNameContaining(name); 

        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if nothing found
        }
        return ResponseEntity.ok(tasks);
    }
    // Endpoint: PUT /tasks/{id}/execute (Execute a task) [cite: 77]
    @PutMapping("/{id}/execute")
    public ResponseEntity<Task> executeTask(@PathVariable String id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOpt.get();
        Date startTime = new Date();
        String output = "";

        try {
            // This part runs the command on your computer
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", task.getCommand());
            Process process = processBuilder.start();
            
            // This part reads the output from the command
            StringBuilder outputBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    outputBuilder.append(line).append("\n");
                }
            }
            output = outputBuilder.toString();
            process.waitFor();

        } catch (Exception e) {
            output = "Execution failed: " + e.getMessage();
        }
        
        Date endTime = new Date();
        
        // Create the execution record
        TaskExecution execution = new TaskExecution();
        execution.setStartTime(startTime);
        execution.setEndTime(endTime);
        execution.setOutput(output);
        
        task.getTaskExecutions().add(execution);
        Task updatedTask = taskRepository.save(task); // Save back to DB
        
        return ResponseEntity.ok(updatedTask);
    }
}