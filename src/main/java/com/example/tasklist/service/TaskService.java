package com.example.tasklist.service;

import com.example.tasklist.model.Status;
import com.example.tasklist.model.Task;
import com.example.tasklist.repository.TaskRepository;
import com.example.tasklist.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repo;
    private final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public Task create(Task t) {
        logger.info("Creating task: {}", t.getTitle());
        Task saved = repo.save(t);
        logger.debug("Saved task with id: {}", saved.getId());
        return saved;
    }

    public List<Task> list(Optional<Status> status) {
        if (status.isPresent()) {
            logger.info("Listing tasks with status {}", status.get());
            return repo.findByStatus(status.get());
        } else {
            logger.info("Listing all tasks");
            return repo.findAll();
        }
    }

    public Task markComplete(Long id) {
        logger.info("Marking task {} as COMPLETED", id);
        Task t = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        if (t.getStatus() == Status.COMPLETED) {
            logger.warn("Task {} is already completed", id);
            return t;
        }
        t.setStatus(Status.COMPLETED);
        Task updated = repo.save(t);
        logger.debug("Task {} updated status to {}", id, updated.getStatus());
        return updated;
    }

    public Task getById(Long id) {
        logger.debug("Fetching task by id {}", id);
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }
}
