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

import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Vehicle;

/**
 * Implementation of the Vehicle interface.
 *
 * This class represents a vehicle that can carry various types of containers
 * with specific capacities. It includes methods to add capacities, check if a
 * container can be picked, and other vehicle-related functionalities.
 */
public class VehicleImp implements Vehicle {

    /**
     * The maximum number of different container capacities the vehicle can
     * handle initially.
     */
    private static final int MAX_OBJECT = 10;

    /**
     * The rate at which the capacity array expands when more space is needed.
     */
    private static final int EXPANTION_RATE = 2;

    /**
     * The number of different container capacities currently in the vehicle.
     */
    private int nCapacity;

    /**
     * The unique code identifying this vehicle.
     */
    private String code;

    /**
     * An array holding the different container capacities this vehicle can
     * carry.
     */
    private Capacity[] capacity;

    /**
     * Indicates whether the vehicle is enabled or not.
     */
    private boolean isEnabled;

    /**
     * Constructs a new VehicleImp with the specified code.
     *
     * @param code The unique code for this vehicle.
     */
    public VehicleImp(String code) {
        this.code = code;
        this.capacity = new Capacity[MAX_OBJECT];
    }

    /**
     * Copy constructor to create a deep copy of another VehicleImp.
     *
     * @param other The VehicleImp to copy.
     */
    public VehicleImp(VehicleImp other) {
        this.code = other.code;
        this.capacity = new Capacity[MAX_OBJECT];
    }

    /**
     * Finds the index of the specified capacity in the capacity array.
     *
     * @param cpct The capacity to find.
     * @return The index of the capacity if found, -1 otherwise.
     */
    private int findCapacity(Capacity cpct) {
        for (int i = 0; i < this.nCapacity; i++) {
            if ((this.capacity[i]).equals(cpct)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Expands the capacity array to accommodate more capacities.
     */
    private void expand() {
        Capacity[] tmpCap = new Capacity[this.capacity.length * EXPANTION_RATE];

        for (int i = 0; i < this.nCapacity; i++) {
            tmpCap[i] = this.capacity[i];
        }
        this.capacity = tmpCap;
    }

    /**
     * Adds a new capacity to the vehicle.
     *
     * @param cpct The capacity to add.
     * @return true if the capacity was added, false if it was already present.
     * @throws VehicleException if the capacity is null.
     */
    public boolean addCapacity(Capacity cpct) throws VehicleException {

        if (this.nCapacity == this.capacity.length) {
            expand();
        }

        if (cpct == null) {
            throw new VehicleException("Container inválido");
        }

        if (findCapacity(cpct) != -1) {
            return false;
        }

        this.capacity[this.nCapacity++] = cpct;
        return true;
    }

    /**
     * Gets the capacity for the specified container type.
     *
     * @param ct The container type.
     * @return The capacity for the container type, or -1 if not found.
     */
    @Override
    public double getCapacity(ContainerType ct) {

        for (int i = 0; i < this.nCapacity; i++) {
            if (this.capacity[i].getType().equals(ct)) {
                return capacity[i].getCapacity();
            }
        }

        return -1;
    }

    /**
     * Sets whether the vehicle is enabled.
     *
     * @param enable true to enable the vehicle, false to disable it.
     */
    public void setEnable(boolean enable) {
        this.isEnabled = enable;
    }

    /**
     * Gets the unique code identifying this vehicle.
     *
     * @return The unique code of the vehicle.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Checks if the vehicle is enabled.
     *
     * @return true if the vehicle is enabled, false otherwise.
     */
    public boolean isEnable() {
        return this.isEnabled;
    }

    /**
     * Checks if this vehicle is equal to another object.
     *
     * @param obj The object to compare.
     * @return true if the objects are equal, false otherwise.
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

        return this.code.equals(((VehicleImp) obj).code);
    }

    /**
     * Converts the capacities of the vehicle to a string representation.
     *
     * @return A string representation of the vehicle's capacities.
     */
    private String capacityToString() {
        int nCont = 0;
        String s = " ";

        for (Capacity cpct : this.capacity) {
            if (this.capacity[nCont] != null) {
                s += "\n" + this.capacity[nCont++].toString();
            }
        }
        return s;
    }

    /**
     * Converts the vehicle to a string representation.
     *
     * @return A string representation of the vehicle.
     */
    @Override
    public String toString() {
        String s = capacityToString();
        return "\n\nVehicleImp{" + ", code=" + code + s + '}';
    }

    /**
     * Checks if the vehicle can pick the specified container.
     *
     * @param container The container to check.
     * @return true if the vehicle can pick the container, false otherwise.
     */
    public boolean canPick(Container container) {
        if (container == null) {
            return false;
        }

        ContainerType containerType = container.getType();
        double containerCapacity = container.getCapacity();

        for (int i = 0; i < nCapacity; i++) {
            Capacity vehicleCapacity = capacity[i];
            if (vehicleCapacity.getType().equals(containerType) && vehicleCapacity.getCapacity() >= containerCapacity) {
                return true;
            }
        }
        return false;
    }

}
