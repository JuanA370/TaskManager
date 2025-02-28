package com.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskPriority;
import com.taskmanager.model.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByAssignedUserId(Long userId);

	List<Task> findByStatus(TaskStatus status);

	List<Task> findByPriority(TaskPriority priority);
}