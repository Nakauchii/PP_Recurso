/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
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
 * Represents a report generated for picking operations in an institution.
 * This class provides statistics such as used vehicles, picked containers,
 * total distance, total duration, non-picked containers, non-used vehicles,
 * and the date of the report.
 */
public class ReportImp implements com.estg.pickingManagement.Report {

    private int nUsedVehicles;
    private int nPickedContainers;
    private double totalDistance;
    private double totalDuration;
    private int notPickedContainers;
    private int notUsedVehicles;
    private LocalDateTime date;

    /**
     * Constructs a ReportImp object with specified statistics and date.
     *
     * @param nUsedVehicles Number of vehicles used in the operations.
     * @param nPickedContainers Number of containers successfully picked.
     * @param totalDistance Total distance covered during operations.
     * @param totalDuration Total duration of the operations.
     * @param notPickedContainers Number of containers that were not picked.
     * @param notUsedVehicles Number of vehicles that were not used.
     * @param date Date and time when the report was generated.
     */
    public ReportImp(int nUsedVehicles, int nPickedContainers, double totalDistance,
                     double totalDuration, int notPickedContainers, int notUsedVehicles,
                     LocalDateTime date) {
        this.nUsedVehicles = nUsedVehicles;
        this.nPickedContainers = nPickedContainers;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.notPickedContainers = notPickedContainers;
        this.notUsedVehicles = notUsedVehicles;
        this.date = date;
    }

    /**
     * Returns the number of vehicles used in the operations.
     *
     * @return The number of vehicles used.
     */
    @Override
    public int getUsedVehicles() {
        return nUsedVehicles;
    }

    /**
     * Returns the number of containers successfully picked.
     *
     * @return The number of picked containers.
     */
    @Override
    public int getPickedContainers() {
        return nPickedContainers;
    }

    /**
     * Returns the total distance covered during the operations.
     *
     * @return The total distance covered.
     */
    @Override
    public double getTotalDistance() {
        return totalDistance;
    }

    /**
     * Returns the total duration of the operations.
     *
     * @return The total duration in hours.
     */
    @Override
    public double getTotalDuration() {
        return totalDuration;
    }

    /**
     * Returns the number of containers that were not picked.
     *
     * @return The number of non-picked containers.
     */
    @Override
    public int getNonPickedContainers() {
        return notPickedContainers;
    }

    /**
     * Returns the number of vehicles that were not used.
     *
     * @return The number of non-used vehicles.
     */
    @Override
    public int getNotUsedVehicles() {
        return notUsedVehicles;
    }

    /**
     * Returns the date and time when the report was generated.
     *
     * @return The date and time of the report.
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the number of used vehicles in the report.
     *
     * @param nUsedVehicles Number of vehicles used in the operations.
     */
    public void setUsedVehicles(int nUsedVehicles) {
        this.nUsedVehicles = nUsedVehicles;
    }

    /**
     * Sets the number of picked containers in the report.
     *
     * @param nPickedContainers Number of containers successfully picked.
     */
    public void setPickedContainers(int nPickedContainers) {
        this.nPickedContainers = nPickedContainers;
    }

    /**
     * Sets the total distance covered during the operations in the report.
     *
     * @param totalDistance Total distance covered during operations.
     */
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * Sets the total duration of the operations in the report.
     *
     * @param totalDuration Total duration of the operations.
     */
    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    /**
     * Sets the number of non-picked containers in the report.
     *
     * @param notPickedContainers Number of containers that were not picked.
     */
    public void setNotPickedContainers(int notPickedContainers) {
        this.notPickedContainers = notPickedContainers;
    }

    /**
     * Sets the number of non-used vehicles in the report.
     *
     * @param notUsedVehicles Number of vehicles that were not used.
     */
    public void setNotUsedVehicles(int notUsedVehicles) {
        this.notUsedVehicles = notUsedVehicles;
    }

    /**
     * Sets the date and time of the report.
     *
     * @param date Date and time when the report was generated.
     */
    public void setDate(LocalDateTime date){
        this.date = date;
    }

    /**
     * Returns a string representation of the report.
     * The string includes all the report's statistics and the date.
     *
     * @return A string representation of the report.
     */
    public String toString(){
        String s = "";
        s += "Date: " + this.date + "\n";
        s += "Used Vehicles: " + this.nUsedVehicles + "\n";
        s += "Non-Used Vehicles: " + this.notUsedVehicles + "\n";
        s += "Picked Containers: " + this.nPickedContainers + "\n";
        s += "Non-Picked Containers: " + this.notPickedContainers + "\n";
        s += "Total Distance of Operation: " + this.totalDistance + "\n";
        s += "Total Duration of Operation: " + this.totalDuration + "\n";

        return s;
    }

}
