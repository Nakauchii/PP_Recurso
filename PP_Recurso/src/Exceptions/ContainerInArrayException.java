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

import com.estg.core.exceptions.ContainerException;

/**
 * Exception thrown when there is an issue with a container in the array.
 */
public class ContainerInArrayException extends ContainerException {

    /**
     * Constructs a new ContainerInArrayException with {@code null} as its
     * detail message.
     */
    public ContainerInArrayException() {
    }

    /**
     * Constructs a new ContainerInArrayException with the specified detail
     * message.
     *
     * @param message the detail message
     */
    public ContainerInArrayException(String message) {
        super(message);
    }

}
