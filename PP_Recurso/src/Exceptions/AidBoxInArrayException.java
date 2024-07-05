/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package Exceptions;

import com.estg.core.exceptions.AidBoxException;

/**
 * Custom exception class for handling situations where an aid box operation fails due to array-related issues.
 * Extends AidBoxException to maintain compatibility with existing exception handling mechanisms.
 */
public class AidBoxInArrayException extends AidBoxException {

    /**
     * Constructs a new AidBoxInArrayException with no detail message.
     */
    public AidBoxInArrayException() {
    }

    /**
     * Constructs a new AidBoxInArrayException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public AidBoxInArrayException(String message) {
        super(message);
    }
}
