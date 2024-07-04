package Alert;

import java.time.LocalDateTime;

public class Alert {
    private String description;
    private LocalDateTime timestamp;

    private Object object;

    public Alert(String description, Object obj) {
        this.object = obj;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public Alert(Alert other) {
        this.object = other.getObject();
        this.description = other.getDescription();
        this.timestamp = other.getTimestamp();
    }

    public Object getObject(){
        return object;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
