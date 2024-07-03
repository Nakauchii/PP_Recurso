/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Vehicle;

/**
 *
 * @author Roger Nakauchi
 */
public class VehicleImp implements Vehicle {
    
    private String code;
    //private VehicleCapacitiesImp capacities;
    private int numberContainerTypes;
    private boolean isEnable;
    private double code1;
    
    public VehicleImp(String code, int numberContainerTypes) {
        this.code = code;
        //this.capacities = new VehicleCapacitiesImp(numberContainerTypes);
        this.isEnable = true;
    }
    
    public boolean isEnabled() {
        return isEnable;
    }

    public void setEnabled(boolean enabled) {
        this.isEnable = enabled;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public double getCapacity(ContainerType ct) {
        return this.code1;
    }
    
    public void addContainerCapacity(ContainerType containerType, double capacity) {
        //capacities.addCapacity(containerType, capacity);
    }
    
}
