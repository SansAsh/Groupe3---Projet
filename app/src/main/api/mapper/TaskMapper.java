package com.esieeit.projetsi.api.mapper;

import com.esieeit.projetsi.api.dto.TaskResponse;
import com.esieeit.projetsi.domain.model.Task;

/**
 * Convertit les entités domain Task en DTOs de réponse API.
 * Classe utilitaire statique — pas d'instanciation.
 */
public final class TaskMapper {

    private TaskMapper() {}

    /**
     * Convertit une entité Task en TaskResponse (DTO).
     *
     * @param task l'entité domain
     * @return le DTO prêt à sérialiser en JSON
     */
    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name()
        );
    }
}