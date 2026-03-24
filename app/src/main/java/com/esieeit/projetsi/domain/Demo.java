package com.esieeit.projetsi.domain;

import java.util.Set;

import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.exception.ValidationException;
import com.esieeit.projetsi.domain.model.Comment;
import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.domain.model.User;

/**
 * Programme de démonstration CLI — TP 2.2.
 * Prouve que les entités, validations et workflow fonctionnent.
 * Exécuter : ./gradlew run  (ou java -cp out com.esieeit.projetsi.domain.Demo)
 */
public class Demo {

    public static void main(String[] args) {

        System.out.println("=== TP 2.2 - Démonstration du domaine métier ===\n");

        // ── 1. Créer des utilisateurs ──────────────────────────────
        System.out.println("--- Création des utilisateurs ---");
        User alice = new User("alice@example.com", "alice", Set.of(UserRole.USER));
        User bob   = new User("bob@example.com",   "bob",   Set.of(UserRole.USER, UserRole.ADMIN));
        System.out.println(alice);
        System.out.println(bob);
        System.out.println("Bob est admin ? " + bob.hasRole(UserRole.ADMIN));
        System.out.println();

        // ── 2. Créer un projet ─────────────────────────────────────
        System.out.println("--- Création du projet ---");
        Project projet = new Project("Projet SI Java", "API REST de gestion de tâches", alice);
        System.out.println(projet);
        System.out.println("Projet appartient à alice ? " + projet.isOwnedBy(alice));
        System.out.println();

        // ── 3. Créer des tâches ────────────────────────────────────
        System.out.println("--- Création des tâches ---");
        Task t1 = new Task("Initialiser le repo Git", "Setup Gradle + README + .gitignore", projet);
        Task t2 = new Task("Implémenter les entités", null, projet);
        t1.assignTo(alice);
        t2.assignTo(bob);
        System.out.println(t1);
        System.out.println(t2);
        System.out.println();

        // ── 4. Workflow de statut (chemin nominal) ─────────────────
        System.out.println("--- Workflow de statut (chemin nominal) ---");
        System.out.println("Statut initial t1 : " + t1.getStatus());
        t1.start();
        System.out.println("Après start()    : " + t1.getStatus());
        t1.complete();
        System.out.println("Après complete() : " + t1.getStatus());
        t1.archive();
        System.out.println("Après archive()  : " + t1.getStatus());
        System.out.println();

        // ── 5. Transition interdite ────────────────────────────────
        System.out.println("--- Transitions interdites ---");
        Task t3 = new Task("Tâche test transitions", null, projet);

        // Essai complete() depuis TODO (interdit)
        try {
            t3.complete();
        } catch (BusinessRuleException e) {
            System.out.println("✓ Erreur attendue (TODO→DONE) : " + e.getMessage());
        }

        // Essai archive() depuis TODO (interdit)
        try {
            t3.archive();
        } catch (BusinessRuleException e) {
            System.out.println("✓ Erreur attendue (TODO→ARCHIVED) : " + e.getMessage());
        }

        // Passer en IN_PROGRESS puis essayer reopen()
        t3.start();
        t3.reopen();
        System.out.println("Après reopen() : " + t3.getStatus()); // doit être TODO
        System.out.println();

        // ── 6. Validations techniques ──────────────────────────────
        System.out.println("--- Validations techniques ---");

        // Email invalide
        try {
            new User("pas-un-email", "charlie", Set.of(UserRole.USER));
        } catch (ValidationException e) {
            System.out.println("✓ Email invalide détecté : " + e.getMessage());
        }

        // Username trop court
        try {
            new User("charlie@example.com", "ab", Set.of(UserRole.USER));
        } catch (ValidationException e) {
            System.out.println("✓ Username trop court détecté : " + e.getMessage());
        }

        // Nom de projet vide
        try {
            new Project("", null, alice);
        } catch (ValidationException e) {
            System.out.println("✓ Nom de projet vide détecté : " + e.getMessage());
        }

        // Contenu de commentaire vide
        try {
            new Comment("", t2, alice);
        } catch (ValidationException e) {
            System.out.println("✓ Commentaire vide détecté : " + e.getMessage());
        }
        System.out.println();

        // ── 7. Commentaire valide ──────────────────────────────────
        System.out.println("--- Commentaire valide ---");
        t2.start();
        Comment c = new Comment("Entités User et Project terminées ✓", t2, bob);
        System.out.println(c);
        System.out.println();

        System.out.println("=== Démonstration terminée avec succès ===");
    }
}