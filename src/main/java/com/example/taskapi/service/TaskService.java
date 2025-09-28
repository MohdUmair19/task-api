package com.example.taskapi.service;

import com.example.taskapi.model.Task;
import com.example.taskapi.model.TaskExecution;
import com.example.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(Task task) {
        if (isUnsafeCommand(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command detected!");
        }
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findByName(String name) {
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

    public TaskExecution executeTask(String taskId) throws Exception {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        TaskExecution execution = new TaskExecution();
        execution.setStartTime(new Date());

        Process process = Runtime.getRuntime().exec(task.getCommand());
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        process.waitFor();
        execution.setEndTime(new Date());
        execution.setOutput(output.toString());

        task.getTaskExecutions().add(execution);
        taskRepository.save(task);

        return execution;
    }

    private boolean isUnsafeCommand(String command) {
        String lower = command.toLowerCase();
        return lower.contains("rm ") || lower.contains("shutdown") || lower.contains("reboot");
    }
}
