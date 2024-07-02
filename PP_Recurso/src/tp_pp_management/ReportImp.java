/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.pickingManagement.Report;
import java.time.LocalDateTime;
import tp_pp_classes.ContainerDataImp;
import tp_pp_classes.VehicleDataImp;

/**
 *
 * @author Roger Nakauchi
 */
public class ReportImp implements Report {
    
    private LocalDateTime date;
    private ContainerDataImp containerData;
    private VehicleDataImp vehicleData;
    
    public ReportImp(int containerCapacity, int vehicleCapacity) {
        this.date = LocalDateTime.now();
        this.containerData = new ContainerDataImp(containerCapacity);
        this.vehicleData = new VehicleDataImp(vehicleCapacity);
    }
    
    

    @Override
    public int getUsedVehicles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getPickedContainers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getTotalDistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getTotalDuration() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getNonPickedContainers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getNotUsedVehicles() {
        return vehicleData.getNotUsedCount();
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }
    
}
