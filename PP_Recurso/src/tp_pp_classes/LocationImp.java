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

import java.util.Objects;

/**
 * Represents a location with a specific code, distance, and duration.
 *
 * This class provides methods to retrieve the location code, distance, and
 * duration.
 */
public class LocationImp {

    /**
     * The unique code of the location.
     */
    private String code;

    /**
     * The distance to the location in kilometers.
     */
    private double distance;

    /**
     * The duration to reach the location in minutes.
     */
    private double duration;

    /**
     * Constructs a new LocationImp instance with the specified code, distance,
     * and duration.
     *
     * @param code The unique code of the location.
     * @param distance The distance to the location in kilometers.
     * @param duration The duration to reach the location in minutes.
     */
    public LocationImp(String code, double distance, double duration) {
        this.code = code;
        this.distance = distance;
        this.duration = duration;
    }

    /**
     * Gets the code of the location.
     *
     * @return The code of the location.
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the distance to the location.
     *
     * @return The distance to the location in kilometers.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Gets the duration to reach the location.
     *
     * @return The duration to reach the location in minutes.
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false
     * otherwise.
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
        final LocationImp other = (LocationImp) obj;
        if (Double.doubleToLongBits(this.distance) != Double.doubleToLongBits(other.distance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.duration) != Double.doubleToLongBits(other.duration)) {
            return false;
        }
        return Objects.equals(this.code, other.code);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Location{" + "code=" + code + ", distance=" + distance + ", duration=" + duration + '}';
    }
}
