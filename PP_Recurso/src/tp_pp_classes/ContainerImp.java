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

import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Implementation of the Container interface, representing a container that
 * holds measurements of a specific item type. This class handles operations
 * related to containers such as adding and retrieving measurements.
 *
 */
public class ContainerImp implements Container {

    /**
     * The unique identifier of the container.
     */
    private String id;

    /**
     * The unique code identifying the container.
     */
    private String code;

    /**
     * The capacity of the container.
     */
    private int capacity;

    /**
     * The type of the container.
     */
    private ContainerType type;

    /**
     * Array of measurements associated with the container.
     */
    private Measurement[] measurements;

    /**
     * The current number of measurements in the container.
     */
    private int numberMeasurements;
    
    /**
     * Indicates if the container has been picked.
     */
    private boolean picked;

    /**
     * The maximum initial capacity of the measurements array.
     */
    private final int MAX = 2;

    /**
     * Constructs a new ContainerImp with the specified id, code, capacity, and type.
     *
     * @param id the unique identifier of the container
     * @param code the code of the container
     * @param capacity the capacity of the container
     * @param type the type of the container
     */
    public ContainerImp(String id, String code, int capacity, ContainerType type) {
        this.id = id;
        this.code = code;
        this.capacity = capacity;
        this.type = type;
        this.measurements = new MeasurementImp[MAX];
    }

    /**
     * Returns the code of the container.
     *
     * @return the code of the container
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Returns the capacity of the container.
     *
     * @return the capacity of the container
     */
    @Override
    public double getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the type of the container.
     *
     * @return the type of the container
     */
    @Override
    public ContainerType getType() {
        return this.type;
    }

    /**
     * Returns the unique identifier of the container.
     *
     * @return the unique identifier of the container
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * Checks if the container has been picked.
     *
     * @return true if the container has been picked, false otherwise
     */
    public boolean isPicked() {
        return picked;
    }
    
    /**
     * Sets the picked status of the container.
     *
     * @param picked the picked status to set
     */
    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    /**
     * Returns an array of measurements associated with the container.
     *
     * @return an array of measurements associated with the container
     */
    @Override
    public Measurement[] getMeasurements() {
        Measurement[] copyMeasurements = new Measurement[numberMeasurements];
        for (int i = 0; i < numberMeasurements; i++) {
            if (measurements[i] != null) {
                copyMeasurements[i] = new MeasurementImp((MeasurementImp) this.measurements[i]);
            }
        }
        return copyMeasurements;
    }

    /**
     * Returns an array of measurements taken on a specific date.
     *
     * @param ld the date to filter the measurements
     * @return an array of measurements taken on the specified date
     */
    @Override
    public Measurement[] getMeasurements(LocalDate ld) {
        Measurement[] copyMeasurementsDate = new Measurement[numberMeasurements];
        int count = 0;

        for (int i = 0; i < numberMeasurements; i++) {
            try {
                if (measurements[i].getDate().toLocalDate().equals(ld)) {
                    copyMeasurementsDate[count++] = new MeasurementImp((MeasurementImp) this.measurements[i]);
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return copyMeasurementsDate;
    }

    /**
     * Adds a measurement to the container.
     *
     * @param msrmnt the measurement to add
     * @return true if the measurement was added successfully, false otherwise
     * @throws MeasurementException if the measurement is null, has a negative value, or the date is invalid
     */
    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {
        if (msrmnt == null) {
            throw new MeasurementException();
        }
        if (msrmnt.getValue() < 0) {
            throw new MeasurementException();
        }
        if (numberMeasurements > 0) {
            if (msrmnt.getDate().isBefore(measurements[numberMeasurements - 1].getDate())) {
                throw new MeasurementException();
            }
        }

        if (((MeasurementImp) msrmnt).getContainerCode().equals(code)){
            return false;
        }

        if (numberMeasurements == measurements.length) {
            Measurement[] newMeasurements = new Measurement[measurements.length * 2];
            for (int i = 0; i < numberMeasurements; i++) {
                newMeasurements[i] = measurements[i];
            }
            measurements = newMeasurements;
        }
        measurements[numberMeasurements++] = msrmnt;
        return true;
    }

    /**
     * Compares this container to the specified object.
     * The result is true if and only if the argument is not null and is a ContainerImp object
     * that has the same code as this object.
     *
     * @param obj the object to compare this container against
     * @return true if the given object represents a container equivalent to this container, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ContainerImp)) {
            return false;
        }
        ContainerImp container = (ContainerImp) obj;
        return this.code == container.code;
    }

    /**
     * Returns a string representation of the container.
     *
     * @return a string representation of the container
     */
    @Override
    public String toString() {
        String result =  "ContainerImp{" + "id=" + id + ", code=" + code + ", capacity=" + capacity + type + "\n"
        + "Measurements:\n";

        for (int i = 0; i < numberMeasurements; i++) {
            if (measurements[i] != null) {
                result += measurements[i].toString() + "\n";
            }
        }
        return result;
    }

}
