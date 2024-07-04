/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */

package Alert;

import java.time.LocalDateTime;

/**
 * A class that represents an alert with a description, timestamp, and associated object.
 * This class is used to store invalid data obtained through the API, identifying the date of occurrence.
 */
public class Alert {
    private String description;
    private LocalDateTime timestamp;
    private Object object;

    /**
     * Constructs an Alert with the specified description and associated object.
     * The timestamp is set to the current date and time.
     *
     * @param description A description of the alert.
     * @param obj The object associated with the alert.
     */
    public Alert(String description, Object obj) {
        this.object = obj;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs a new Alert that is a copy of the specified Alert.
     *
     * @param other The Alert to copy.
     */
    public Alert(Alert other) {
        this.object = other.getObject();
        this.description = other.getDescription();
        this.timestamp = other.getTimestamp();
    }

    /**
     * Returns the object associated with the alert.
     *
     * @return The object associated with the alert.
     */
    public Object getObject(){
        return object;
    }

    /**
     * Returns the description of the alert.
     *
     * @return The description of the alert.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the timestamp of the alert.
     *
     * @return The timestamp of the alert.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a string representation of the alert.
     * The string representation includes the description and timestamp of the alert.
     *
     * @return A string representation of the alert.
     */
    @Override
    public String toString() {
        return "Alert{" +
                "description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
