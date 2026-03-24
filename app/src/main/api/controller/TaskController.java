package com.esieeit.projetsi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST pour la ressource Task.
 * Expose le CRUD complet sur /api/tasks.
 *
 * Règle : le controller ne contient PAS de logique métier.
 * Il orchestre uniquement : requête HTTP → service → réponse HTTP.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // Injection par constructeur (recommandé sur @Autowired)
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * GET /api/tasks
     * Retourne la liste de toutes les tâches.
     * HTTP 200 OK
     */
    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.getAll()
                .stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/tasks/{id}
     * Retourne une tâche par son ID.
     * HTTP 200 OK | 404 (géré en TP 3.2 avec @ControllerAdvice)
     */
    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return TaskMapper.toResponse(task);
    }

    /**
     * POST /api/tasks
     * Crée une nouvelle tâche.
     * HTTP 201 Created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody TaskCreateRequest req) {
        Task task = taskService.create(req);
        return TaskMapper.toResponse(task);
    }

    /**
     * PUT /api/tasks/{id}
     * Met à jour une tâche existante.
     * HTTP 200 OK | 404
     */
    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @RequestBody TaskUpdateRequest req) {
        Task task = taskService.update(id, req);
        return TaskMapper.toResponse(task);
    }

    /**
     * DELETE /api/tasks/{id}
     * Supprime une tâche.
     * HTTP 204 No Content | 404
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}