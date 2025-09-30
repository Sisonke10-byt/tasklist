package com.example.tasklist.controller;

import com.example.tasklist.model.Status;
import com.example.tasklist.model.Task;
import com.example.tasklist.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody Task task) {
        logger.info("POST /api/tasks - payload title='{}', dueDate='{}'", task.getTitle(), task.getDueDate());
        logger.debug("Current date: {}", LocalDate.now());
        Task created = service.create(task);
        return ResponseEntity.created(URI.create("/api/tasks/" + created.getId())).body(created);
    }

    @GetMapping
    public List<Task> list(@RequestParam(value = "status", required = false) String status) {
        Optional<Status> s = Optional.empty();
        if (status != null) {
            try {
                s = Optional.of(Status.valueOf(status.toUpperCase()));
            } catch (IllegalArgumentException ex) {
                // invalid status -> return empty list or throw bad request. We'll log and
                // return all.
                logger.warn("Invalid status filter '{}', returning all tasks", status);
            }
        }
        return service.list(s);
    }

    @PutMapping("/{id}/complete")
    public Task markComplete(@PathVariable Long id) {
        logger.info("PUT /api/tasks/{}/complete", id);
        return service.markComplete(id);
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable Long id) {
        return service.getById(id);
    }
}
