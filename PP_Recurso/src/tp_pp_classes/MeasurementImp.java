/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Roger Nakauchi
 */
public class MeasurementImp implements Measurement {

    private String containerCode;
    private LocalDateTime date;
    private double value;

    public MeasurementImp(String containerCode, LocalDateTime date, double value) {
        this.containerCode = containerCode;
        this.date = date;
        this.value = value;
    }
    
    //Construtor para o deep copy
    public MeasurementImp(MeasurementImp other) {
        this.containerCode = other.containerCode;
        this.date = other.date;
        this.value = other.value;
    }

    public String getContainerCode() {
        return containerCode;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Measurement{containerCode: " + containerCode + "date: " + date + ", value: " + value + "}";
    }

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
