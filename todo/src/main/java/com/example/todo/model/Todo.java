package com.example.todo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Task title (required)
    private String task;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    private LocalDate deadline;

    // ✅ Constructors
    public Todo() {
        this.createdAt = LocalDate.now();
        this.completed = false;
    }

    public Todo(String task, LocalDate deadline) {
        this.task = task;
        this.deadline = deadline;
        this.completed = false;
        this.createdAt = LocalDate.now();
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTask() { return task; }

    public void setTask(String task) { this.task = task; }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDate getCreatedAt() { return createdAt; } // 🔹 No setter (auto-managed)

    public LocalDate getDeadline() { return deadline; }

    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
}
