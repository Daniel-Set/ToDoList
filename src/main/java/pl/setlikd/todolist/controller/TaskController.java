package pl.setlikd.todolist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.setlikd.todolist.model.Task;
import pl.setlikd.todolist.model.TaskRepository;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
class TaskController {
    private final TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> creatTask(@RequestBody @Valid Task toCreat) {
        Task result = taskRepository.save(toCreat);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping(value = "/tasks")
    ResponseEntity<?> readAllTasks(Pageable page) {
        logger.warn("Custom page");
        return ResponseEntity.ok(taskRepository.findAll((org.springframework.data.domain.Pageable) page).getContent());
    }

    @GetMapping("tests/{id}")
    ResponseEntity<Task> readTaskById(@PathVariable Integer id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody @Valid Task toUpdate) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();

        }
        toUpdate.setId(id);
        taskRepository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }


}
