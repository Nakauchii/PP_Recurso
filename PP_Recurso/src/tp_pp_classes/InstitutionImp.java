/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import java.time.LocalDateTime;
import tp_pp_management.Report;
import tp_pp_management.VehicleImp;

/**
 *
 * @author fabio
 */
public class InstitutionImp implements Institution {

    private static final int MAX = 10;
    private static final int EXPAND = 2;

    private int nAidBox;
    private int nVehicle;
    private int nPickingMap;
    private int nReport;
    private AidBox[] aidBoxes;
    private Container[] containers;
    private Vehicle[] vehicles;
    private String name;
    private PickingMap[] pickingMaps;
    private Report[] reports;

    public InstitutionImp(String name) {
        this.name = name;
        this.nReport = 0;
        this.nAidBox = 0;
        this.nVehicle = 0;
        this.aidBoxes = new AidBoxImp[MAX];
        this.vehicles = new VehicleImp[MAX];
        this.pickingMaps = new PickingMap[MAX];
        this.reports = new Report[MAX];
    }

    @Override
    public String getName() {
        return this.name;
    }

    private AidBox findAidBox(AidBox aidBox) {
        for (int i = 0; i < nAidBox; i++) {
            if (this.aidBoxes[i].equals(aidBox)) {
                return aidBoxes[i];
            }
        }
        return null;
    }

    private boolean hasDuplicateContainers(AidBox aidbox) {
        Container[] containers = aidbox.getContainers();
        for (int i = 0; i < containers.length; i++) {
            for (int j = i + 1; j < containers.length; j++) {
                if (i != j && containers[i].getType().equals(containers[j].getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void expandAidBox() {
        AidBox[] aidBox = new AidBoxImp[this.aidBoxes.length * EXPAND];

        for (int i = 0; i < this.nAidBox; i++) {
            aidBox[i] = this.aidBoxes[i];
        }
        this.aidBoxes = aidBox;
    }

    @Override
    public boolean addAidBox(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException();
        }

        if (findAidBox(aidbox) != null) {
            return false;
        }

        if (hasDuplicateContainers(aidbox)) {
            throw new AidBoxException();
        }

        if (this.nAidBox == this.aidBoxes.length) {
            expandAidBox();
        }

        this.aidBoxes[nAidBox++] = aidbox;
        return true;
    }

    private boolean hasContainer(Container ct) {
        for (int i = 0; i < nAidBox; i++) {
            Container container = this.aidBoxes[i].getContainer(ct.getType());
            if (container.equals(ct)) {
                return true;
            }
        }
        return false;
    }

    private Measurement findMeasuremet(Measurement msrmnt, Container cntnr) {
        Measurement[] measurements = cntnr.getMeasurements();

        for (int i = 0; i < measurements.length; i++) {
            if (measurements[i].equals(msrmnt)) {
                return measurements[i];
            }
        }
        return null;
    }

    @Override
    public boolean addMeasurement(Measurement msrmnt, Container cntnr) throws ContainerException, MeasurementException {
        if (hasContainer(cntnr) == false) {
            throw new ContainerException();
        }

        if (findMeasuremet(msrmnt, cntnr) != null) {
            return false;
        }
        
        //if(msrmnt.getCode != cntnr.getCode())
        if (msrmnt.getValue() > cntnr.getCapacity()) {
            throw new ContainerException();
        }

        try {
            cntnr.addMeasurement(msrmnt);
        } catch (MeasurementException exc) {
            throw new ContainerException();
        }

        return true;
    }

    @Override
    public AidBox[] getAidBoxes() {
        AidBox[] aidBox = new AidBoxImp[nAidBox];
        for (int i = 0; i < nAidBox; i++) {
            if (aidBoxes[i] != null) {
                aidBox[i] = aidBoxes[i];
            }
        }
        return aidBox;
    }

    @Override
    public Container getContainer(AidBox aidbox, ContainerType ct) throws ContainerException {
        if (aidbox == null) {
            throw new ContainerException("Aidbox doesn´t exist.");
        }

        Container[] containers = aidbox.getContainers();

        for (int i = 0; i < containers.length; i++) {
            if (containers[i] != null && containers[i].getType().equals(ct)) {
                return containers[i];
            }
        }

        throw new ContainerException("Container with the given item type doesn't exist.");
    }

    private Vehicle findVehicle(Vehicle vhcl) {
        for (int i = 0; i < this.nVehicle; i++) {
            if (this.vehicles[i].equals(vhcl)) {
                return vehicles[i];
            }
        }
        return null;
    }

    private void expandVehicle() {
        Vehicle[] tmpVehicle = new VehicleImp[this.vehicles.length * EXPAND];

        for (int i = 0; i < this.nVehicle; i++) {
            tmpVehicle[i] = this.vehicles[i];
        }
        this.vehicles = tmpVehicle;
    }

    @Override
    public Vehicle[] getVehicles() {
        Vehicle[] tmpVehicle = new VehicleImp[this.nVehicle];

        for (int i = 0; i < this.nVehicle; i++) {
            tmpVehicle[i] = this.vehicles[i];
        }

        return tmpVehicle;
    }

    @Override
    public boolean addVehicle(Vehicle vhcl) throws VehicleException {
        if (this.nVehicle == this.vehicles.length) {
            expandVehicle();
        }

        if (vhcl == null) {
            return false;
        }

        if (findVehicle(vhcl) != null) {
            return false;
        }

        this.vehicles[this.nVehicle++] = vhcl;
        return true;
    }

    @Override
    public void disableVehicle(Vehicle vhcl) throws VehicleException {
        if (findVehicle(vhcl) == null) {
            throw new VehicleException();
        }

        VehicleImp myVehicle = (VehicleImp) vhcl;
        if (!myVehicle.isEnable()) {
            throw new VehicleException();
        }

        myVehicle.setEnable(false);
    }

    @Override
    public void enableVehicle(Vehicle vhcl) throws VehicleException {
        if (findVehicle(vhcl) == null) {
            throw new VehicleException();
        }

        VehicleImp myVehicle = (VehicleImp) vhcl;
        if (!myVehicle.isEnable()) {
            throw new VehicleException();
        }

        myVehicle.setEnable(false);
    }

    private int findPickingMap(PickingMap pm) {
        for (int i = 0; i < nPickingMap; i++) {
            if (this.pickingMaps[i].equals(pm)) {
                return i;
            }
        }
        return -1;
    }

    private void expandPickingMap() {
        PickingMap[] PMap = new PickingMap[this.pickingMaps.length * EXPAND];

        for (int i = 0; i < this.nPickingMap; i++) {
            PMap[i] = this.pickingMaps[i];
        }
        this.pickingMaps = PMap;
    }

    @Override
    public boolean addPickingMap(PickingMap pm) throws PickingMapException {
        if (this.nPickingMap == this.pickingMaps.length) {
            expandPickingMap();
        }

        if (pm == null) {
            throw new PickingMapException("The Picking Map is null");
        }

        if (findPickingMap(pm) != -1) {
            return false;
        }

        this.pickingMaps[this.nPickingMap++] = pm;
        return true;
    }

    @Override
    public PickingMap[] getPickingMaps() {
        PickingMap[] Pm = new PickingMap[this.nPickingMap];
        for (int i = 0; i < this.nPickingMap; i++) {
            Pm[i] = this.pickingMaps[i];
        }
        return Pm;
    }

    @Override
    public PickingMap[] getPickingMaps(LocalDateTime ldt, LocalDateTime ldt1) {
        PickingMap[] copy = new PickingMap[nPickingMap];

        for (int i = 0; i < nPickingMap; i++) {
            LocalDateTime date = pickingMaps[i].getDate();
            if (date.isAfter(ldt) && date.isBefore(ldt1)) {
                copy[i] = pickingMaps[i];
            }
        }
        return copy;
    }

    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {
        if (nPickingMap == 0) {
            throw new PickingMapException("There are no picking maps in the institution");
        }
        PickingMap currentPickingMap = pickingMaps[0];
        for (int i = 1; i < nPickingMap; i++) {
            if (pickingMaps[i].getDate().isAfter(currentPickingMap.getDate())) {
                currentPickingMap = pickingMaps[i];
            }
        }
        return currentPickingMap;
    }

    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("Aid Box is Invalid");
        }

        LocationImp location = ((AidBoxImp) aidbox).getLocation("Base");
        if (location == null) {
            throw new AidBoxException("Aid Box not found");
        }

        return location.getDistance();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof InstitutionImp)) {
            return false;
        }
        InstitutionImp inst = (InstitutionImp) obj;
        return this.name == inst.name;
    }

}
