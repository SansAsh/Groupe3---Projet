package com.esieeit.projetsi.domain.enums;

/**
 * Statuts possibles d'une tâche.
 * Les transitions autorisées sont gérées dans {@link com.esieeit.projetsi.domain.model.Task}.
 */
public enum TaskStatus {
    /** Tâche créée, pas encore démarrée. État initial. */
    TODO,
    /** Tâche en cours de réalisation. */
    IN_PROGRESS,
    /** Tâche terminée. */
    DONE,
    /** Tâche archivée. État final — lecture seule. */
    ARCHIVED
}