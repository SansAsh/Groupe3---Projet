# Structure de packages - Projet SI Java (ESIEE‑IT 2025-2026)

---

## Objectif

Éviter un projet "fourre-tout" où tout est dans un seul package.
On applique une **Clean Architecture simplifiée** avec séparation claire des responsabilités :

- **domain** : règles métier, entités, enums — ne dépend de personne
- **application** : orchestration des cas d'usage (services)
- **api** : exposition REST (controllers + DTO)
- **infrastructure** : persistance JPA, configuration (séances 4 et 5)

---

## Arborescence complète

```
src/main/java/com/esieeit/projetsi/
│
├── domain/
│   ├── model/
│   │   ├── User.java
│   │   ├── Project.java
│   │   ├── Task.java
│   │   └── Comment.java
│   ├── enums/
│   │   ├── TaskStatus.java
│   │   ├── TaskPriority.java
│   │   └── UserRole.java
│   └── exception/
│       ├── ResourceNotFoundException.java
│       ├── InvalidStatusTransitionException.java
│       └── UnauthorizedException.java
│
├── application/
│   └── service/
│       ├── UserService.java
│       ├── ProjectService.java
│       ├── TaskService.java
│       └── CommentService.java
│
├── api/
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── ProjectController.java
│   │   ├── TaskController.java
│   │   └── CommentController.java
│   └── dto/
│       ├── request/
│       │   ├── CreateProjectRequest.java
│       │   ├── CreateTaskRequest.java
│       │   └── CreateCommentRequest.java
│       └── response/
│           ├── ProjectResponse.java
│           ├── TaskResponse.java
│           └── UserResponse.java
│
└── infrastructure/
    ├── persistence/
    │   ├── UserRepository.java
    │   ├── ProjectRepository.java
    │   ├── TaskRepository.java
    │   └── CommentRepository.java
    └── config/
        ├── SecurityConfig.java       ← séance 5
        └── ApplicationConfig.java
```

---

## Rôle de chaque package

| Package                        | Rôle                                                        |
|--------------------------------|-------------------------------------------------------------|
| `domain/model`                 | Entités métier pures (POJO Java, pas de Spring)             |
| `domain/enums`                 | Enums métier : TaskStatus, TaskPriority, UserRole           |
| `domain/exception`             | Exceptions métier personnalisées                            |
| `application/service`          | Logique métier, orchestration des cas d'usage               |
| `api/controller`               | Endpoints REST — reçoit requêtes HTTP, retourne réponses    |
| `api/dto/request`              | Objets entrants (ce que le client envoie)                   |
| `api/dto/response`             | Objets sortants (ce que l'API retourne)                     |
| `infrastructure/persistence`   | Interfaces JPA Repository (séance 4)                        |
| `infrastructure/config`        | Configuration Spring (sécurité, beans, etc.)                |

---

## Règles de dépendances (IMPORTANT)

```
api  →  application  →  domain
infrastructure  →  application + domain

INTERDIT :
- domain  →  quoi que ce soit  (domain est indépendant)
- application  →  api           (pas de dépendance remontante)
- domain  →  Spring Framework   (pas d'annotations Spring dans domain)
```

### En clair :
- ✅ `Controller` appelle `Service`
- ✅ `Service` manipule les entités `domain/model`
- ✅ `Service` utilise `Repository` (via interface)
- ❌ `Service` ne connaît pas `Controller`
- ❌ `Model` ne connaît pas `Service` ni Spring
- ❌ `Controller` n'appelle pas directement `Repository`

---

## Conventions de nommage

| Élément       | Convention             | Exemple                    |
|---------------|------------------------|----------------------------|
| Entités       | PascalCase, singulier  | `User`, `Project`, `Task`  |
| Services      | PascalCase + Service   | `ProjectService`           |
| Controllers   | PascalCase + Controller| `ProjectController`        |
| DTO Request   | PascalCase + Request   | `CreateProjectRequest`     |
| DTO Response  | PascalCase + Response  | `ProjectResponse`          |
| Repositories  | PascalCase + Repository| `ProjectRepository`        |
| Enums         | PascalCase             | `TaskStatus`               |
| Valeurs enum  | SCREAMING_SNAKE_CASE   | `IN_PROGRESS`, `TODO`      |
| Packages      | lowercase              | `domain`, `service`        |

---

## Évolutions prévues (séances suivantes)

| Séance | Ajouts dans la structure                              |
|--------|-------------------------------------------------------|
| S3     | `api/controller/*` + `api/dto/*` (Spring Boot REST)  |
| S4     | `infrastructure/persistence/*` (JPA + annotations)   |
| S5     | `infrastructure/config/SecurityConfig` (JWT)         |