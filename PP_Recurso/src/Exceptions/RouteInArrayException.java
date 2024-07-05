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

import com.estg.pickingManagement.exceptions.RouteException;

/**
 * Exception thrown when there is an issue with a route in the array.
 */
public class RouteInArrayException extends RouteException {

    /**
     * Constructs a new RouteInArrayException with {@code null} as its detail
     * message.
     */
    public RouteInArrayException() {
    }

    /**
     * Constructs a new RouteInArrayException with the specified detail message.
     *
     * @param message the detail message
     */
    public RouteInArrayException(String message) {
        super(message);
    }
}
