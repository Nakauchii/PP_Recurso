/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package tp_pp_classes;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;

import java.util.Objects;

/**
 * Implementation of the {@link AidBox} interface representing an aid box with a
 * unique code and zone. This class manages containers and locations associated
 * with the aid box.
 *
 * This class also provides methods for adding, finding, and removing containers
 * and locations, as well as calculating distances and durations between aid
 * boxes.
 *
 */
public class AidBoxImp implements AidBox {

    /**
     * The unique identifier of the aid box.
     */
    private String id;

    /**
     * The code of the aid box.
     */
    private String code;

    /**
     * The zone of the aid box.
     */
    private String zone;

    /**
     * Array of locations associated with the aid box.
     */
    private LocationImp[] locations;

    /**
     * Array of containers within the aid box.
     */
    private Container[] containers;

    /**
     * The current number of containers in the aid box.
     */
    private int numberContainers;

    /**
     * The maximum capacity of the aid box.
     */
    private final int MAX = 4;

    /**
     * Factor by which the capacity of the aid box is expanded.
     */
    private final int EXPAND = 2;

    /**
     * The current number of locations in the aid box.
     */
    private int nLocations;

    /**
     * Constructs a new AidBoxImp with the specified id, code, and zone.
     *
     * @param id the unique identifier for the aid box
     * @param code the code for the aid box
     * @param zone the zone for the aid box
     */
    public AidBoxImp(String id, String code, String zone) {
        this.id = id;
        this.code = code;
        this.zone = zone;
        this.containers = new ContainerImp[MAX];
        this.locations = new LocationImp[MAX];
        this.numberContainers = 0;
    }

    /**
     * Constructs a new AidBoxImp as a deep copy of the specified AidBoxImp.
     *
     * @param other the AidBoxImp to copy
     */
    public AidBoxImp(AidBoxImp other) {
        this.id = other.id;
        this.code = other.code;
        this.zone = other.zone;
        this.containers = new ContainerImp[MAX];
        this.locations = new LocationImp[MAX];
        this.numberContainers = 0;
    }

    /**
     * Code representing the aid box.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Zone in which the aid box is located.
     */
    @Override
    public String getZone() {
        return this.zone;
    }

    /**
     * Retrieves the location with the specified code.
     *
     * @param code the code of the location to retrieve
     * @return the location with the specified code, or null if not found
     */
    public LocationImp getLocation(String code) {
        for (int i = 0; i < this.nLocations; i++) {
            if (this.locations[i].getCode().equals(code)) {
                return locations[i];
            }
        }
        return null;
    }

    /**
     * Finds the location in the array of locations.
     *
     * @param loc the location to find
     * @return the found location, or null if not found
     */
    private LocationImp findLocation(LocationImp loc) {
        for (int i = 0; i < this.nLocations; i++) {
            if (this.locations[i].equals(loc)) {
                return locations[i];
            }
        }
        return null;
    }

    /**
     * Expands the array of locations.
     */
    private void expandLocation() {
        LocationImp[] location = new LocationImp[this.locations.length * EXPAND];

        for (int i = 0; i < this.nLocations; i++) {
            location[i] = this.locations[i];
        }
        this.locations = location;
    }

    /**
     * Adds a new location to the aid box.
     *
     * @param loc the location to add
     * @return true if the location was added, false otherwise
     * @throws AidBoxException if the location is null or already exists
     */
    public boolean addLocation(LocationImp loc) throws AidBoxException {
        if (loc == null) {
            return false;
        }

        if (findLocation(loc) != null) {
            return false;
        }

        if (nLocations >= locations.length) {
            expandLocation();
        }

        this.locations[nLocations++] = loc;
        return true;
    }

    /**
     * Calculates the distance to the specified aid box.
     *
     * @param aidbox the aid box to calculate the distance to
     * @return the distance to the specified aid box
     * @throws AidBoxException if the aid box is invalid or not found
     */
    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("Aid Box is Invalid");
        }

        LocationImp location = getLocation(aidbox.getCode());
        if (location == null) {
            throw new AidBoxException("Aid Box not found");
        }

        return location.getDistance();
    }

    /**
     * Calculates the duration to the specified aid box.
     *
     * @param aidbox the aid box to calculate the duration to
     * @return the duration to the specified aid box
     * @throws AidBoxException if the aid box is invalid or not found
     */
    @Override
    public double getDuration(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("Aid Box is Invalid");
        }

        String aidBoxCode = aidbox.getCode();
        LocationImp location = getLocation(aidBoxCode);
        if (location == null) {
            throw new AidBoxException("Aid Box not found");
        }

        return location.getDuration();
    }

    /**
     * Finds the container in the array of containers.
     *
     * @param cntnr the container to find
     * @return the found container, or null if not found
     */
    private Container findContainer(Container cntnr) {
        for (int i = 0; i < this.numberContainers; i++) {
            if (this.containers[i].equals(cntnr)) {
                return containers[i];
            }
        }
        return null;
    }

    /**
     * Adds a new container to the aid box.
     *
     * @param cntnr the container to add
     * @return true if the container was added, false otherwise
     * @throws ContainerException if the container is null or already exists
     */
    @Override
    public boolean addContainer(Container cntnr) throws ContainerException {
        if (cntnr == null) {
            throw new ContainerException("Container cannot be null");
        }

        if (findContainer(cntnr) != null) {
            return false;
        }

        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null && containers[i].getType().equals(cntnr.getType())) {
                throw new ContainerException("AidBox already has a container of type: " + cntnr.getType());
            }
        }

        if (numberContainers < containers.length) {
            containers[numberContainers++] = cntnr;
            return true;
        } else {
            throw new ContainerException("Cannot add more containers. AidBox is full.");
        }
    }

    /**
     * Retrieves the container of the specified type.
     *
     * @param ct the type of container to retrieve
     * @return the container of the specified type, or null if not found
     */
    @Override
    public Container getContainer(ContainerType ct) {
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i].getType().equals(ct)) {
                return containers[i];
            }
        }
        return null;
    }

    /**
     * Returns an array of containers in this aid box.
     *
     * @return an array of containers in this aid box
     */
    @Override
    public Container[] getContainers() {
        Container[] result = new Container[numberContainers];
        for (int i = 0; i < numberContainers; i++) {
            result[i] = containers[i];
        }
        return result;
    }

    /**
     * Removes the specified container from this aid box.
     *
     * @param cntnr the container to remove
     * @throws AidBoxException if the container is null or does not exist
     */
    @Override
    public void removeContainer(Container cntnr) throws AidBoxException {
        if (cntnr == null) {
            throw new AidBoxException("Container is null.");
        }

        boolean found = false;
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i].equals(cntnr)) {
                found = true;
                containers[i] = containers[--numberContainers];
                containers[numberContainers] = null;
                break;
            }
        }

        if (!found) {
            throw new AidBoxException("Container does not exist.");
        }
    }

    /**
     * Returns a string representation of this aid box.
     *
     * @return a string representation of this aid box
     */
    @Override
    public String toString() {
        String result = "Code: " + code + "\n"
                + "Zone: " + zone + "\n"
                + "Containers:\n";

        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null) {
                result += containers[i].toString() + "\n";
            }
        }

        return result;
    }


    /**
     * Checks if there is at least one perishable container among the stored containers.
     *
     * @return {@code true} if there is at least one perishable container, {@code false} otherwise.
     */
    public boolean hasPerishableContainer() {
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i].getType() instanceof ContainerTypeImp) {
                if (((ContainerTypeImp) containers[i].getType()).isPerishable()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public double getCurrentLoadPercentage() {
        double totalCapacity = 0;
        double currentLoad = 0;
        
        for(int i = 0; i < numberContainers; i++) {
            totalCapacity += containers[i].getCapacity();
            currentLoad += ((ContainerImp) containers[i]).getLoad();
        }
        
        if(totalCapacity == 0) {
            return 0;
        } else {
            return (currentLoad / totalCapacity) * 100;
        }
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false
     * otherwise
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
        final AidBoxImp other = (AidBoxImp) obj;
        return Objects.equals(this.code, other.code);
    }


}
