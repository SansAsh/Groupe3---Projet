# Backlog Produit - Projet SI Java (ESIEE‑IT 2025-2026)

---

## Pitch produit

Notre API permet à un utilisateur de **gérer ses projets et ses tâches** de manière structurée.
Elle vise des équipes ou étudiants qui veulent **organiser leur travail collaboratif**.
Elle fournit une **API REST sécurisée** avec authentification JWT.

---

## Hypothèses & contraintes

- Projet **Java / Spring Boot** (API uniquement, pas de frontend)
- Auth obligatoire (login / register minimum)
- MVP réalisable en **9 séances de 3h**
- Backlog orienté **fonctionnel**

---

## Acteurs

| Acteur     | Objectifs                        | Permissions                          |
|------------|----------------------------------|--------------------------------------|
| Visiteur   | Découvrir l'app, s'inscrire      | S'inscrire uniquement                |
| Utilisateur| Gérer ses projets et tâches      | CRUD projets, CRUD tâches            |
| Admin      | Superviser la plateforme         | Voir / bloquer / supprimer comptes   |

---

## Modules / Features

- **A.** Authentification & Profil
- **B.** Gestion des projets
- **C.** Gestion des tâches
- **D.** Recherche / Filtre
- **E.** Administration *(bonus)*

---

## User Stories

| ID    | Module | User Story                                                                                                      | Priorité | Estimation |
|-------|--------|-----------------------------------------------------------------------------------------------------------------|----------|------------|
| US-01 | Auth   | En tant que **Visiteur**, je veux **créer un compte** afin de **pouvoir accéder à l'application**.             | Must     | M          |
| US-02 | Auth   | En tant que **Utilisateur**, je veux **me connecter** afin de **retrouver mes projets**.                        | Must     | M          |
| US-03 | Auth   | En tant que **Utilisateur**, je veux **me déconnecter** afin de **sécuriser ma session**.                       | Should   | S          |
| US-04 | Auth   | En tant que **Utilisateur**, je veux **modifier mon profil** afin de **mettre à jour mes informations**.        | Should   | S          |
| US-05 | Projet | En tant que **Utilisateur**, je veux **créer un projet** afin de **structurer mon travail**.                    | Must     | M          |
| US-06 | Projet | En tant que **Utilisateur**, je veux **lister mes projets** afin de **voir tout ce que je gère**.               | Must     | S          |
| US-07 | Projet | En tant que **Utilisateur**, je veux **modifier un projet** afin de **corriger ou compléter ses informations**. | Must     | S          |
| US-08 | Projet | En tant que **Utilisateur**, je veux **supprimer un projet** afin de **nettoyer ma liste**.                     | Should   | S          |
| US-09 | Tâche  | En tant que **Utilisateur**, je veux **ajouter une tâche dans un projet** afin de **planifier les actions**.    | Must     | M          |
| US-10 | Tâche  | En tant que **Utilisateur**, je veux **changer le statut d'une tâche** afin de **suivre l'avancement**.         | Must     | S          |
| US-11 | Tâche  | En tant que **Utilisateur**, je veux **modifier une tâche** afin d'**ajuster son contenu**.                     | Should   | S          |
| US-12 | Tâche  | En tant que **Utilisateur**, je veux **supprimer une tâche** afin d'**enlever les tâches inutiles**.            | Should   | S          |
| US-13 | Filtre | En tant que **Utilisateur**, je veux **filtrer les tâches par statut** afin de **me concentrer sur les urgentes**. | Nice  | M          |
| US-14 | Filtre | En tant que **Utilisateur**, je veux **rechercher une tâche par mot‑clé** afin de **retrouver rapidement une info**. | Nice | M        |
| US-15 | Admin  | En tant que **Admin**, je veux **lister tous les utilisateurs** afin de **surveiller la plateforme**.           | Nice     | M          |

---

## Critères d'acceptation

### US-01 — Créer un compte

- **Given** je suis un visiteur non connecté
- **When** je fournis un email valide et un mot de passe d'au moins 8 caractères
- **Then** mon compte est créé et je peux me connecter
- **And** si l'email est déjà utilisé → message d'erreur `"Email déjà utilisé"`
- **And** si le mot de passe est trop court → message d'erreur `"8 caractères minimum"`
- **And** si l'email est invalide → message d'erreur `"Format d'email invalide"`

### US-02 — Se connecter

- **Given** je suis un utilisateur inscrit
- **When** je fournis mon email et mon mot de passe corrects
- **Then** je reçois un token JWT valide et j'accède à mes données
- **And** si le mot de passe est incorrect → message d'erreur `"Identifiants invalides"`
- **And** si le compte n'existe pas → message d'erreur `"Identifiants invalides"`

### US-03 — Se déconnecter

- **Given** je suis connecté avec un token JWT valide
- **When** je fais une requête de déconnexion
- **Then** mon token est invalidé
- **And** toute requête avec l'ancien token retourne une erreur `401`

### US-04 — Modifier mon profil

- **Given** je suis connecté
- **When** je modifie mon nom ou mon email avec des valeurs valides
- **Then** mes informations sont mises à jour en base
- **And** si le nouvel email est déjà pris → message d'erreur `"Email déjà utilisé"`

### US-05 — Créer un projet

- **Given** je suis connecté
- **When** je crée un projet avec un nom et une description valides
- **Then** le projet est sauvegardé et associé à mon compte
- **And** le projet apparaît dans ma liste de projets
- **And** si le nom est vide → message d'erreur `"Le nom est obligatoire"`

### US-06 — Lister mes projets

- **Given** je suis connecté et j'ai au moins un projet
- **When** je consulte la liste de mes projets
- **Then** je vois uniquement mes projets (pas ceux des autres utilisateurs)
- **And** si je n'ai aucun projet → la liste retournée est vide

### US-07 — Modifier un projet

- **Given** je suis connecté et je possède un projet
- **When** je modifie le nom ou la description du projet
- **Then** les modifications sont persistées en base
- **And** si le projet ne m'appartient pas → erreur `403 Forbidden`
- **And** si le projet n'existe pas → erreur `404 Not Found`

### US-08 — Supprimer un projet

- **Given** je suis connecté et je possède un projet
- **When** je supprime ce projet
- **Then** le projet et toutes ses tâches sont supprimés
- **And** si le projet ne m'appartient pas → erreur `403 Forbidden`

### US-09 — Ajouter une tâche dans un projet

- **Given** je suis connecté et j'ai un projet existant
- **When** je crée une tâche avec un titre dans ce projet
- **Then** la tâche est créée avec le statut `TODO` par défaut
- **And** la tâche est associée au bon projet
- **And** si le projet n'existe pas → erreur `404 Not Found`
- **And** si le titre est vide → message d'erreur `"Le titre est obligatoire"`

### US-10 — Changer le statut d'une tâche

- **Given** je suis connecté et je possède une tâche
- **When** je change le statut de la tâche (`TODO`, `IN_PROGRESS`, `DONE`)
- **Then** le statut est mis à jour en base
- **And** si la valeur de statut est invalide → message d'erreur `"Statut invalide"`
- **And** si la tâche ne m'appartient pas → erreur `403 Forbidden`

### US-11 — Modifier une tâche

- **Given** je suis connecté et je possède une tâche
- **When** je modifie le titre ou la description de la tâche
- **Then** les modifications sont persistées
- **And** si la tâche ne m'appartient pas → erreur `403 Forbidden`

### US-12 — Supprimer une tâche

- **Given** je suis connecté et je possède une tâche
- **When** je supprime cette tâche
- **Then** la tâche est supprimée et n'apparaît plus dans la liste du projet
- **And** si la tâche ne m'appartient pas → erreur `403 Forbidden`

### US-13 — Filtrer les tâches par statut

- **Given** je suis connecté et j'ai des tâches avec différents statuts
- **When** je filtre par statut `DONE`
- **Then** seules les tâches avec ce statut sont retournées
- **And** si aucune tâche ne correspond → la liste retournée est vide

### US-14 — Rechercher une tâche par mot‑clé

- **Given** je suis connecté et j'ai des tâches
- **When** je recherche par le mot-clé `"API"`
- **Then** toutes les tâches dont le titre ou la description contient `"API"` sont retournées
- **And** la recherche est insensible à la casse
- **And** si aucun résultat → liste vide

### US-15 — Lister tous les utilisateurs (Admin)

- **Given** je suis connecté en tant qu'Admin
- **When** je consulte la liste des utilisateurs
- **Then** je vois tous les comptes inscrits sur la plateforme
- **And** si je ne suis pas Admin → erreur `403 Forbidden`

---

## Définition of Done (DoD)

Une story est considérée **Done** quand :

- [ ] Le code compile sans erreur
- [ ] Les tests passent (`./gradlew test`)
- [ ] La PR est ouverte et mergée vers `develop`
- [ ] Le `BACKLOG.md` est mis à jour si besoin
- [ ] Aucun fichier de build (`build/`, `.gradle/`) dans Git

---

## Résumé MoSCoW

| Priorité | Stories                                      | Total |
|----------|----------------------------------------------|-------|
| Must     | US-01, US-02, US-05, US-06, US-07, US-09, US-10 | 7  |
| Should   | US-03, US-04, US-08, US-11, US-12            | 5     |
| Nice     | US-13, US-14, US-15                          | 3     |