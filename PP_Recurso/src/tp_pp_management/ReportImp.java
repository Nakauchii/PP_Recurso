/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Route;
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
    private Route route;
    
    public ReportImp(Route route) {
        this.route = route;
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
    
    //Não tenho ctz desse metodo
    private int calculateUsedVehicles() {
        Institution[] institutions = (Institution[]) route.getRoute();
        int count = 0;
        for(int i = 0; i < institutions.length; i++) {
            Vehicle[] vehicles = institutions[i].getVehicles();
            for(int j = 0; j < vehicles.length; j++) {
                if(((VehicleImp) vehicles[j]).isEnable()) {
                    count++;
                }
            }
        }
        return count;
    }
    
    
    public int calculatePickedContainers() {
        AidBox[] aidboxes = route.getRoute();
        int count = 0;
        for(int i = 0; i < aidboxes.length; i++) {
            Container[] containers = aidboxes[i].getContainers();
            for(int j = 0; j < containers.length; j++) {
                if(((ContainerImp) containers[j]).isPicked()) {
                    count++;
                }
            }
        }
        return count;
    }
    
    
    public double calculateTotalDistance() {
        double totalDistance = 0;
        AidBox[] aidboxes = route.getRoute();
        for(int i = 0; i < aidboxes.length - 1; i++) {
            try {
                totalDistance += aidboxes[i].getDistance(aidboxes[i + 1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalDistance;
    }
    
    
    public double calculateTotalDuration() {
        double totalDuration = 0;
        AidBox[] aidboxes = route.getRoute();
        for(int i = 0; i < aidboxes.length - 1; i++) {
            try {
                totalDuration += aidboxes[i].getDuration(aidboxes[i + 1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalDuration;
    }
    
    public int calculateNonPickedContainers() {
        AidBox[] aidboxes = route.getRoute();
        int count = 0;
        for(int i = 0; i < aidboxes.length; i++) {
            Container[] containers = aidboxes[i].getContainers();
            for(int j = 0; j < containers.length; j++) {
                if(!((ContainerImp) containers[i]).isPicked()) {
                    count++;
                }
            }
        }
        return count;
    }
    
    //Tb, não tenho ctz desse metodo
    private int calculateNotUsedVehicles() {
        Institution[] institutions = (Institution[]) route.getRoute();
        int count = 0;
        for(int i = 0; i < institutions.length; i++) {
            Vehicle[] vehicles = institutions[i].getVehicles();
            for(int j = 0; j < vehicles.length; j++) {
                if(!((VehicleImp) vehicles[j]).isEnable()) {
                    count++;
                }
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
