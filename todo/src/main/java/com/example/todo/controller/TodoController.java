package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // Show all todos with filter options
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "all") String filter, Model model) {
        List<Todo> todos;

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        switch (filter) {
            case "completed":
                todos = todoRepository.findByCompleted(true);
                break;
            case "incomplete":
                todos = todoRepository.findByCompleted(false);
                break;
            case "thisWeek":
                todos = todoRepository.findByDeadlineBetween(startOfWeek, endOfWeek);
                break;
            case "nextWeek":
                LocalDate nextWeekStart = startOfWeek.plusWeeks(1);
                LocalDate nextWeekEnd = endOfWeek.plusWeeks(1);
                todos = todoRepository.findByDeadlineBetween(nextWeekStart, nextWeekEnd);
                break;
            default:
                todos = todoRepository.findAll();
        }

        model.addAttribute("todos", todos);
        model.addAttribute("filter", filter);
        model.addAttribute("newTodo", new Todo()); // for add form
        return "index";
    }

    // Add new todo
    @PostMapping("/add")
    public String addTodo(@ModelAttribute("newTodo") Todo todo) {
        todoRepository.save(todo);
        return "redirect:/";
    }

    // Show Edit page
    @GetMapping("/edit/{id}")
    public String editTodo(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        model.addAttribute("todo", todo);
        model.addAttribute("newTodo", new Todo()); // for add form in edit page
        model.addAttribute("filter", "all");
        model.addAttribute("todos", todoRepository.findAll());
        return "edit"; // edit.html
    }

    // Update todo
    @PostMapping("/update")
    public String updateTodo(@ModelAttribute Todo todo) {
        todoRepository.save(todo);
        return "redirect:/";
    }

    // Mark complete/incomplete
    @GetMapping("/toggle/{id}")
    public String toggleCompleted(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
        return "redirect:/";
    }

    // Delete a todo
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/";
    }
}
