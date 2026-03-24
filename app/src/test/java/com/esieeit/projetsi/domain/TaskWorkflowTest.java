package com.esieeit.projetsi.domain;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.domain.model.User;

/**
 * Tests unitaires du workflow de statut de Task et des validations.
 */
class TaskWorkflowTest {

    private User user;
    private Project project;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        user = new User("test@example.com", "testuser", Set.of(UserRole.USER));
        project = new Project("Projet Test", null, user);
    }

    // ────── Workflow nominal ─────────────────────────────────

    @Test
    void newTask_shouldHaveStatusTodo() {
        Task task = new Task("Ma tâche", null, project);
        assertEquals(TaskStatus.TODO, task.getStatus());
    }

    // ────── Transitions interdites ───────────────────────────

    @Test
    void complete_shouldFail_whenStatusIsTodo() {
        Task task = new Task("Ma tâche", null, project);
        BusinessRuleException ex =
                assertThrows(BusinessRuleException.class, task::complete);
        assertNotNull(ex.getMessage());
    }

    @Test
    void archive_shouldFail_whenStatusIsTodo() {
        Task task = new Task("Ma tâche", null, project);
        BusinessRuleException ex =
                assertThrows(BusinessRuleException.class, task::archive);
        assertNotNull(ex.getMessage());
    }

    @Test
    void archive_shouldFail_whenStatusIsInProgress() {
        Task task = new Task("Ma tâche", null, project);
        task.start();
        BusinessRuleException ex =
                assertThrows(BusinessRuleException.class, task::archive);
        assertNotNull(ex.getMessage());
    }

    @Test
    void start_shouldFail_whenStatusIsDone() {
        Task task = new Task("Ma tâche", null, project);
        task.start();
        task.complete();
        BusinessRuleException ex =
                assertThrows(BusinessRuleException.class, task::start);
        assertNotNull(ex.getMessage());
    }
}
