/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.Container;
import com.estg.pickingManagement.Vehicle;
import java.time.LocalDateTime;
import tp_pp_classes.ContainerImp;
import tp_pp_classes.DataManager;
import tp_pp_classes.LocationImp;

/**
 *
 * @author fabio
 */
public class ReportImp implements com.estg.pickingManagement.Report {
    
    private LocalDateTime date;
    private int usedVehicles;
    private int pickedContainers;
    private double totalDistance;
    private double totalDuration;
    private int nonPickedContainers;
    private int notUsedVehicles;
    
    private DataManager dataManager;
    
    public ReportImp(DataManager dataManager) {
        this.dataManager = dataManager;
        this.date = LocalDateTime.now();
        generatedReport();
    }
    
    public void generatedReport() {
        this.usedVehicles = calculateUsedVehicles();
        this.pickedContainers = calculatePickedContainers();
        this.totalDistance = calculateTotalDistance();
        this.totalDuration = calculateTotalDuration();
        this.nonPickedContainers = calculateNonPickedContainers();
        this.notUsedVehicles = calculateNotUsedVehicles();
    }
    
    
    private int calculateUsedVehicles() {
        Vehicle[] vehicles = dataManager.getVehicles();
        int count = 0;
        for(int i = 0; i < vehicles.length; i++) {
            Vehicle vehicle = vehicles[i];
            if(((VehicleImp) vehicle).isEnable()) {
                count++;
            }
        }
        return count;
    }
    
    
    public int calculatePickedContainers() {
        Container[] containers = dataManager.getContainers();
        int count = 0;
        for(int i = 0; i < containers.length; i++) {
            Container container = containers[i];
            if(((ContainerImp) container).isPicked()) {
                count++;
            }
        }
        return count;
    }
    
    
    public double calculateTotalDistance() {
        double distance = 0.0;
        LocationImp[] locations = dataManager.getLocations();
        for(int i = 0; i < locations.length; i++) {
            LocationImp location = locations[i];
            distance += location.getDistance();
        }
        return distance;
    }
    
    
    public double calculateTotalDuration() {
        double duration = 0.0;
        LocationImp[] locations = dataManager.getLocations();
        for(int i = 0; i < locations.length; i++) {
            LocationImp location = locations[i];
            duration += location.getDuration();
        }
        return duration;
    }
    
    public int calculateNonPickedContainers() {
        Container[] containers = dataManager.getContainers();
        int count = 0;
        for(int i = 0; i < containers.length; i++) {
            Container container = containers[i];
            if(!((ContainerImp) container).isPicked()) {
                count++;
            }
        }
        return count;
    }
    
    
    private int calculateNotUsedVehicles() {
        Vehicle[] vehicles = dataManager.getVehicles();
        int count = 0;
        for(int i = 0; i < vehicles.length; i++) {
            Vehicle vehicle = vehicles[i];
            if(!((VehicleImp) vehicle).isEnable()) {
                count++;
            }
        }
        return count;
    }
    

    @Override
    public int getUsedVehicles() {
        return usedVehicles;
    }

    @Override
    public int getPickedContainers() {
        return pickedContainers;
    }

    @Override
    public double getTotalDistance() {
        return totalDistance;
    }

    @Override
    public double getTotalDuration() {
        return totalDuration;
    }

    @Override
    public int getNonPickedContainers() {
        return nonPickedContainers;
    }

    @Override
    public int getNotUsedVehicles() {
        return notUsedVehicles;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Report{" +
                "date=" + date +
                ", usedVehicles=" + usedVehicles +
                ", pickedContainers=" + pickedContainers +
                ", totalDistance=" + totalDistance +
                ", totalDuration=" + totalDuration +
                ", nonPickedContainers=" + nonPickedContainers +
                ", notUsedVehicles=" + notUsedVehicles +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReportImp other = (ReportImp) obj;
        return usedVehicles == other.usedVehicles &&
                pickedContainers == other.pickedContainers &&
                Double.compare(other.totalDistance, totalDistance) == 0 &&
                Double.compare(other.totalDuration, totalDuration) == 0 &&
                nonPickedContainers == other.nonPickedContainers &&
                notUsedVehicles == other.notUsedVehicles &&
                date.equals(other.date);
    }
    
}
