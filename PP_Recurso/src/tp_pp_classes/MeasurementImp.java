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

import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a measurement with a container code, date, and value.
 * 
 * This class implements the Measurement interface and provides methods to 
 * retrieve the measurement details.
 */
public class MeasurementImp implements Measurement {

    /**
     * The code of the container where the measurement was taken.
     */
    private String containerCode;
    
    /**
     * The date and time when the measurement was taken.
     */
    private LocalDateTime date;
    
    /**
     * The value of the measurement.
     */
    private double value;

    /**
     * Constructs a new MeasurementImp instance with the specified container code, date, and value.
     * 
     * @param containerCode The code of the container where the measurement was taken.
     * @param date The date and time when the measurement was taken.
     * @param value The value of the measurement.
     */
    public MeasurementImp(String containerCode, LocalDateTime date, double value) {
        this.containerCode = containerCode;
        this.date = date;
        this.value = value;
    }
    
    /**
     * Constructs a new MeasurementImp instance by copying another MeasurementImp instance.
     * 
     * @param other The other MeasurementImp instance to copy.
     */
    public MeasurementImp(MeasurementImp other) {
        this.containerCode = other.containerCode;
        this.date = other.date;
        this.value = other.value;
    }

    /**
     * Gets the code of the container where the measurement was taken.
     * 
     * @return The code of the container.
     */
    public String getContainerCode() {
        return containerCode;
    }

    /**
     * Gets the date and time when the measurement was taken.
     * 
     * @return The date and time of the measurement.
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Gets the value of the measurement.
     * 
     * @return The value of the measurement.
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * Returns a string representation of the measurement.
     * 
     * @return A string representation of the measurement.
     */
    @Override
    public String toString() {
        return "Measurement{containerCode: " + containerCode + "date: " + date + ", value: " + value + "}";
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
        final MeasurementImp other = (MeasurementImp) obj;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        return Objects.equals(this.date, other.date);
    }

}
