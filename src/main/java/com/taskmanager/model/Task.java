package com.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

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

    private boolean completed;
    
    public static void main(String[] args) {
		Task k = new Task();
	}
}
