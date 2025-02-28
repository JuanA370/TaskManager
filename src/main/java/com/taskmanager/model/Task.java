package com.taskmanager.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(length = 500)
	private String description;

	@Column(nullable = false)
	private LocalDateTime dueDate;

	@Enumerated(EnumType.STRING)
	private TaskPriority priority;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User assignedUser; //
}
