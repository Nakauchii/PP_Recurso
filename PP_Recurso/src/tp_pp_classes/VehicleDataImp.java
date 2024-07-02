/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import com.estg.pickingManagement.Vehicle;

/**
 *
 * @author Roger Nakauchi
 */
public class VehicleDataImp {

    private Vehicle[] usedVehicles;
    private Vehicle[] notUsedVehicles;
    private int usedCount;
    private int notUsedCount;
    private double totalDistance;
    private double totalDuration;

    public VehicleDataImp(int initialCapacity) {
        this.usedVehicles = new Vehicle[initialCapacity];
        this.notUsedVehicles = new Vehicle[initialCapacity];
        this.usedCount = 0;
        this.notUsedCount = 0;
        this.totalDistance = 0.0;
        this.totalDuration = 0.0;
    }

    public void addUsedVehicle(Vehicle vehicle, double distance, double duration) {
        if (usedCount == usedVehicles.length) {
            expandUsedVehicles();
        }
        usedVehicles[usedCount++] = vehicle;
        totalDistance += distance;
        totalDuration += duration;
    }

    public void addNotUsedVehicle(Vehicle vehicle) {
        if (notUsedCount == notUsedVehicles.length) {
            expandNotUsedVehicles();
        }
        notUsedVehicles[notUsedCount++] = vehicle;
    }

    private void expandUsedVehicles() {
        Vehicle[] newVehicles = new Vehicle[usedVehicles.length * 2];
        for(int i = 0; i < usedVehicles.length; i++) {
            newVehicles[i] = usedVehicles[i];
        }
        usedVehicles = newVehicles;
    }

    private void expandNotUsedVehicles() {
        Vehicle[] newVehicles = new Vehicle[notUsedVehicles.length * 2];
        for(int i = 0; i < notUsedVehicles.length; i++) {
            newVehicles[i] = notUsedVehicles[i];
        }
        notUsedVehicles = newVehicles;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public int getNotUsedCount() {
        return notUsedCount;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalDuration() {
        return totalDuration;
    }
}
