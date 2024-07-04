/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package tp_pp_management;

import com.estg.core.ContainerType;

/**
 * Represents the capacity of a container of a specific type.
 * 
 * This class provides methods to retrieve the container type and its capacity,
 * as well as methods to check for equality and to provide a string representation
 * of the capacity.
 */
public class Capacity {
    
    /**
     * The type of the container.
     */
    private ContainerType type;
    
    /**
     * The capacity of the container.
     */
    private int capacity;

    /**
     * Constructs a new Capacity instance with the specified container type and capacity.
     * 
     * @param type The type of the container.
     * @param capacity The capacity of the container.
     */
    public Capacity(ContainerType type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    /**
     * Gets the type of the container.
     * 
     * @return The type of the container.
     */
    protected ContainerType getType() {
        return type;
    }

    /**
     * Gets the capacity of the container.
     * 
     * @return The capacity of the container.
     */
    protected int getCapacity() {
        return capacity;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        return this.type.equals(((Capacity) obj).type);
    }

    /**
     * Returns a string representation of the capacity.
     * 
     * @return A string representation of the capacity.
     */
    @Override
    public String toString() {
        return "{" + "type=" + type + ", capacity=" + capacity + '}';
    }
}

