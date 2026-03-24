package com.esieeit.projetsi.domain.exception;

/**
 * Exception levée lorsqu'une validation technique échoue
 * (champ vide, taille invalide, format email incorrect, etc.).
 */
public class ValidationException extends DomainException {

    public ValidationException(String message) {
        super(message);
    }
}