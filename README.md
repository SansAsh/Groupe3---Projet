# Groupe3---Projet
# Projet SI Java - ESIEE‑IT (2025‑2026)

## Contexte

Projet SI en Java : construire une API backend propre, structurée, documentée et testée, avec un workflow Git proche entreprise.

## Objectifs

- Mettre en place un dépôt Git propre (main/develop/feature)
- Implémenter un MVP (auth + gestion de ressources métier)
- Respecter une architecture claire (controller/service/repository)
- Ajouter des tests unitaires
- Produire une documentation exploitable (README + backlog)

## Équipe

- Nom Prénom - rôle (PO / Lead Dev / Dev / QA)
- Nom Prénom - rôle
- Nom Prénom - rôle

## Stack

- Java 17/21
- Gradle (wrapper)
- JUnit 5
- (à venir) Spring Boot, DB, Docker

## Installation

### Prérequis

- Java 17/21
- Git

### Cloner

\`\`\`bash
git clone <URL>
cd <repo>
\`\`\`

## Lancer

### Tests

\`\`\`bash
./gradlew test
\`\`\`

### Run (si application Gradle)

\`\`\`bash
./gradlew run
\`\`\`

## Workflow Git

- **main** : stable
- **develop** : intégration
- **feature/*** : 1 user story = 1 branche
- PR obligatoire vers develop

## Backlog

Voir `BACKLOG.md`.