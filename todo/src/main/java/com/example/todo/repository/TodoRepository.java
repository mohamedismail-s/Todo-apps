package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 🔹 Find todos by status
    List<Todo> findByCompleted(boolean completed);

    // 🔹 Find todos within a date range (by deadline)
    List<Todo> findByDeadlineBetween(LocalDate start, LocalDate end);
}
