package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskPriority;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.service.TaskAiService;
import com.taskmanager.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskAiService taskAiService;

	@GetMapping
	public List<Task> getAllTasks() {
		return taskService.getAllTasks();
	}

	@GetMapping("/{id}")
	public Optional<Task> getTaskById(@PathVariable Long id) {
		return taskService.getTaskById(id);
	}

	@GetMapping("/status/{status}")
	public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
		return taskService.getTasksByStatus(status);
	}

	@GetMapping("/priority/{priority}")
	public List<Task> getTasksByPriority(@PathVariable TaskPriority priority) {
		return taskService.getTasksByPriority(priority);
	}

	@PostMapping
	public Task createTask(@RequestBody Task task) {
		return taskService.createTask(task);
	}

	@PutMapping("/{id}")
	public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
		return taskService.updateTask(id, taskDetails);
	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
	}
	
	@PostMapping("/suggest")
    public String getTaskSuggestion(@RequestBody String description) {
        return taskAiService.generateTaskSuggestion(description);
    }
}
