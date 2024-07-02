/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDateTime;

/**
 *
 * @author Roger Nakauchi
 */
public class MeasurementImp implements Measurement {
    
    private LocalDateTime date;
    private double value;

    public MeasurementImp(LocalDateTime date, double value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public LocalDateTime getDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return "Measurement{date: " + date + ", value: " + value + "}"; 
    }
    
    
    
}
