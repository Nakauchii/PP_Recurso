/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_pp_classes.AidBoxImp;
import tp_pp_classes.DataManager;

/**
 *
 * @author fabio
 */
public class RouteImp implements com.estg.pickingManagement.Route {

    private AidBox[] aidBoxes;
    private int numberOfAidBoxes;
    private Vehicle vehicle;
    private Report report;
    private DataManager dataManager;
    private final int EXPAND = 2;
    private static final int INITIAL_CAPACITY = 10;

    public RouteImp(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.aidBoxes = new AidBox[INITIAL_CAPACITY];
        this.numberOfAidBoxes = 0;
    }


    public void expandAidBoxesArray() {
        AidBox[] newAidboxes = new AidBox[aidBoxes.length * EXPAND];
        for (int i = 0; i < numberOfAidBoxes; i++) {
            newAidboxes[i] = aidBoxes[i];
        }
        aidBoxes = newAidboxes;
    }

    private boolean canVehiclePickAidbox(AidBox aidbox) {
        Container[] containers = aidbox.getContainers();
        for (int i = 0; i < containers.length; i++) {
            if (((VehicleImp) vehicle).canPick(containers[i])) {
                return true;
            }
        }
        return false;
    }

    private AidBox findAidBox(AidBox aid) {
        for (int i = 0; i < numberOfAidBoxes; i++) {
            if (aidBoxes[i].equals(aid)) {
                return aidBoxes[i];
            }
        }
        return null;
    }

    @Override
    public void addAidBox(AidBox aidBox) throws RouteException {
        if (aidBox == null) {
            throw new RouteException();
        }
        if (findAidBox(aidBox) != null) {
            throw new RouteException("AidBox already in the route");
        }
        if (!canVehiclePickAidbox(aidBox)) {
            throw new RouteException();
        }
        if (numberOfAidBoxes >= aidBoxes.length) {
            expandAidBoxesArray();
        }
        aidBoxes[numberOfAidBoxes++] = aidBox;
    }


    @Override
    public AidBox removeAidBox(AidBox aidBox) throws RouteException {
        if (aidBox == null) {
            throw new RouteException();
        }

        AidBox found = findAidBox(aidBox);

        if (found != null) {
            int index = -1;

            for (int i = 0; i < numberOfAidBoxes; i++) {
                if (aidBoxes[i].equals(aidBox)) {
                    index = i;
                }
            }
            if (index != -1){
                AidBox removedAidbox = aidBoxes[index];

                for (int j = 0; j < numberOfAidBoxes - 1; j++) {
                    aidBoxes[j] = aidBoxes[j + 1];
                }
                aidBoxes[--numberOfAidBoxes] = null;
                return removedAidbox;
            }
        }
        throw new RouteException();
    }


    @Override
    public boolean containsAidBox(AidBox aidBox) {
        return findAidBox(aidBox) != null;
    }

    @Override
    public void replaceAidBox(AidBox aidBox, AidBox aidBox1) throws RouteException {
        if(aidBox == null || aidBox1 == null) {
            throw new RouteException();
        }
        if(!containsAidBox(aidBox)) {
            throw new RouteException();
        }
        if(containsAidBox(aidBox1)) {
            throw new RouteException();
        }
        if(!canVehiclePickAidbox(aidBox1)) {
            throw new RouteException();
        }
        for(int i = 0; i < numberOfAidBoxes; i++) {
            if(aidBoxes[i].equals(aidBox)) {
                aidBoxes[i] = aidBox1;
                return;
            }
        }
    }

    @Override
    public void insertAfter(AidBox aidBox, AidBox aidBox1) throws RouteException {
        if(aidBox == null || aidBox1 == null) {
            throw new RouteException();
        }
        if(!containsAidBox(aidBox)) {
            throw new RouteException();
        }
        if(containsAidBox(aidBox1)) {
            throw new RouteException();
        }
        if(!canVehiclePickAidbox(aidBox1)) {
            throw new RouteException();
        }
        if(numberOfAidBoxes >= aidBoxes.length) {
            expandAidBoxesArray();
        }

        for(int i = 0; i < numberOfAidBoxes; i++) {
            if(aidBoxes[i].equals(aidBox)) {
                for(int j = numberOfAidBoxes; j > i + 1; j--) {
                    aidBoxes[j] = aidBoxes[j - 1];
                }
                aidBoxes[i + 1] = aidBox1;
                numberOfAidBoxes++;
            }
        }
            throw new RouteException();
    }

    @Override
    public AidBox[] getRoute() {
        AidBox[] copyRoutes = new AidBox[numberOfAidBoxes];
        for(int i = 0; i < numberOfAidBoxes; i++) {
            copyRoutes[i] = new AidBoxImp((AidBoxImp)aidBoxes[i]);
        }
        return copyRoutes;
    }

    @Override
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    @Override
    public double getTotalDistance() {
        double totalDistance = 0;
        for(int i = 0; i < numberOfAidBoxes; i++) {
            try {
                totalDistance += aidBoxes[i].getDistance(aidBoxes[i + 1]);
            } catch (AidBoxException ex) {
                Logger.getLogger(RouteImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalDistance;
    }

    @Override
    public double getTotalDuration() {
        double totalDuration = 0;
        for(int i = 0; i < numberOfAidBoxes; i++) {
            try {
                totalDuration += aidBoxes[i].getDuration(aidBoxes[i + 1]);
            } catch (AidBoxException ex) {
                Logger.getLogger(RouteImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalDuration;
    }

    @Override
    public Report getReport() {
        return this.report;
    }
}
