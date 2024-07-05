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

/**
 * Constructs a new LocationInArrayException with {@code null} as its detail
 * message.
 */
public class LocationInArrayException extends Exception {

    /**
     * Constructs a new LocationInArrayException with {@code null} as its detail
     * message.
     */
    public LocationInArrayException() {
    }

    /**
     * Constructs a new LocationInArrayException with the specified detail
     * message.
     *
     * @param message the detail message
     */
    public LocationInArrayException(String message) {
        super(message);
    }

}
