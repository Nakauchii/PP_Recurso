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

public class ReportImp implements com.estg.pickingManagement.Report {

    private int nUsedVehicles;
    private int nPickedContainers;
    private double totalDistance;
    private double totalDuration;
    private int notPickedContainers;
    private int notUsedVehicles;
    private LocalDateTime date;

    public ReportImp(int nUsedVehicles, int nPickedContainers, double totalDistance, double totalDuration, int nonPickedContainers, int nonUsedVehicles, LocalDateTime date) {
        this.nUsedVehicles = nUsedVehicles;
        this.nPickedContainers = nPickedContainers;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.notPickedContainers = nonPickedContainers;
        this.notUsedVehicles = nonUsedVehicles;
        this.date = date;
    }

    @Override
    public int getUsedVehicles() {
        return nUsedVehicles;
    }

    @Override
    public int getPickedContainers() {
        return nPickedContainers;
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
        return notPickedContainers;
    }

    @Override
    public int getNotUsedVehicles() {
        return notUsedVehicles;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    public void setUsedVehicles(int nUsedVehicles) {
        this.nUsedVehicles = nUsedVehicles;
    }

    public void setPickedContainers(int nPickedContainers) {
        this.nPickedContainers = nPickedContainers;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void setNotPickedContainers(int notPickedContainers) {
        this.notPickedContainers = notPickedContainers;
    }

    public void setNotUsedVehicles(int notUsedVehicles) {
        this.notUsedVehicles = notUsedVehicles;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }

    public String toString(){
        String s = "";
        s += "Date: " + this.date + "\n";
        s += "Veiculos usados " + this.nUsedVehicles;
        s += "Veiculos nao usados " + this.notUsedVehicles;
        s += "Contentores recolhidos " + this.nPickedContainers;
        s += "Contentores nao recolhidos " + this.notPickedContainers;
        s += "Distancia total da operaçao" + this.totalDistance;
        s += "Duraçao total da operaçao" + this.totalDuration;

        return s;
    }

}
