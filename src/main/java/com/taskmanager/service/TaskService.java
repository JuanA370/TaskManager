package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskPriority;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
	@Autowired
    private TaskRepository taskRepository;

    @Autowired
    private WebSocketNotificationService webSocketService;
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }

    public Task createTask(Task task) {
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(task.getDueDate() != null ? task.getDueDate() : LocalDateTime.now().plusDays(3));
        Task savedTask = taskRepository.save(task);
        webSocketService.sendTaskUpdateNotification("Nueva tarea creada: " + task.getTitle());
        return savedTask;
    }

    public Task updateTask(Long id, Task taskDetails) {
    	 return taskRepository.findById(id).map(task -> {
             task.setTitle(taskDetails.getTitle());
             task.setDescription(taskDetails.getDescription());
             task.setDueDate(taskDetails.getDueDate());
             task.setPriority(taskDetails.getPriority());
             task.setStatus(taskDetails.getStatus());
             Task updatedTask = taskRepository.save(task);
             webSocketService.sendTaskUpdateNotification("Tarea actualizada: " + task.getTitle());
             return updatedTask;
         }).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
        webSocketService.sendTaskUpdateNotification("Una tarea ha sido eliminada.");
    }
}
