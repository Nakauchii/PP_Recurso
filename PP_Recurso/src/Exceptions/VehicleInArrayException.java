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

import com.estg.core.exceptions.VehicleException;

/**
 * Exception thrown when there is an issue with a vehicle in the array.
 */
public class VehicleInArrayException extends VehicleException {

    /**
     * Constructs a new VehicleInArrayException with {@code null} as its detail
     * message.
     */
    public VehicleInArrayException() {
    }

    /**
     * Constructs a new VehicleInArrayException with the specified detail
     * message.
     *
     * @param message the detail message
     */
    public VehicleInArrayException(String message) {
        super(message);
    }
}
