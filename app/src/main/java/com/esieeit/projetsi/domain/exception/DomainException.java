package com.esieeit.projetsi.domain.exception;

/**
 * Exception de base pour toutes les erreurs du domaine métier.
 * Ne pas utiliser RuntimeException directement dans le domaine.
 */
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}

