/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
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
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tp_pp_management.ReportImp;
import tp_pp_management.VehicleImp;

/**
 * Implementation of the {@link Institution} interface representing an
 * institution.
 *
 * This class manages aid boxes, vehicles, picking maps, and reports within an
 * institution. It provides methods to add, retrieve, and manage these
 * components.
 */
public class InstitutionImp implements Institution {

    /**
     * The initial maximum number of objects that can be stored.
     */
    private static final int MAX = 10;

    /**
     * The factor by which arrays are expanded when they reach capacity.
     */
    private static final int EXPAND = 2;

    /**
     * The current number of aid boxes in the institution.
     */
    private int nAidBox;

    /**
     * The current number of vehicles in the institution.
     */
    private int nVehicle;

    /**
     * The current number of picking maps in the institution.
     */
    private int nPickingMap;

    /**
     * The current number of reports in the institution.
     */
    private int nReport;

    /**
     * Array of aid boxes in the institution.
     */
    private AidBox[] aidBoxes;

    /**
     * Array of containers in the institution.
     */
    private Container[] containers;

    /**
     * Array of vehicles in the institution.
     */
    private Vehicle[] vehicles;

    /**
     * Name of the institution.
     */
    private String name;

    /**
     * Array of picking maps in the institution.
     */
    private PickingMap[] pickingMaps;

    /**
     * Array of reports in the institution.
     */
    private ReportImp[] reports;

    /**
     * Constructor for the InstitutionImp class.
     *
     * @param name The name of the institution.
     */
    public InstitutionImp(String name) {
        this.name = name;
        this.nReport = 0;
        this.nAidBox = 0;
        this.nVehicle = 0;
        this.aidBoxes = new AidBoxImp[MAX];
        this.vehicles = new VehicleImp[MAX];
        this.pickingMaps = new PickingMap[MAX];
        this.reports = new ReportImp[MAX];
    }

    /**
     * Gets the name of the institution.
     *
     * @return The name of the institution.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Finds a specific aid box in the institution.
     *
     * @param aidBox The aid box to search for.
     * @return The aid box if found, otherwise null.
     */
    private AidBox findAidBox(AidBox aidBox) {
        for (int i = 0; i < nAidBox; i++) {
            if (this.aidBoxes[i].equals(aidBox)) {
                return aidBoxes[i];
            }
        }
        return null;
    }

    /**
     * Checks if an aid box contains duplicate containers.
     *
     * @param aidbox The aid box to check.
     * @return true if there are duplicate containers, otherwise false.
     */
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

    /**
     * Expands the aid box array when it reaches maximum capacity.
     */
    private void expandAidBox() {
        AidBox[] aidBox = new AidBoxImp[this.aidBoxes.length * EXPAND];

        for (int i = 0; i < this.nAidBox; i++) {
            aidBox[i] = this.aidBoxes[i];
        }
        this.aidBoxes = aidBox;
    }

    /**
     * Adds a new aid box to the institution.
     *
     * @param aidbox The aid box to be added.
     * @return true if the aid box was successfully added, otherwise false.
     * @throws AidBoxException If the aid box is null or contains duplicate containers.
     */
    @Override
    public boolean addAidBox(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("Aid Box is null");
        }

        if (findAidBox(aidbox) != null) {
            return false;
        }

        if (hasDuplicateContainers(aidbox)) {
            throw new AidBoxException("Aid Box is invalid (the type is duplicated)");
        }

        if (this.nAidBox == this.aidBoxes.length) {
            expandAidBox();
        }

        this.aidBoxes[nAidBox++] = aidbox;
        return true;
    }

    /**
     * Checks if a specific container exists in the institution.
     *
     * @param ct The container to check.
     * @return true if the container exists, otherwise false.
     */
    private boolean hasContainer(Container ct) {
        for (int i = 0; i < nAidBox; i++) {
            Container container = this.aidBoxes[i].getContainer(ct.getType());
            if (container.equals(ct)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a specific measurement in a container.
     *
     * @param msrmnt The measurement to find.
     * @param cntnr The container in which to find the measurement.
     * @return The measurement if found, otherwise null.
     */
    private Measurement findMeasuremet(Measurement msrmnt, Container cntnr) {
        Measurement[] measurements = cntnr.getMeasurements();

        for (int i = 0; i < measurements.length; i++) {
            if (measurements[i].equals(msrmnt)) {
                return measurements[i];
            }
        }
        return null;
    }

    /**
     * Adds a measurement to a specific container in the institution.
     *
     * @param msrmnt The measurement to be added.
     * @param cntnr The container to which the measurement will be added.
     * @return true if the measurement was successfully added, otherwise false.
     * @throws ContainerException If the container is not found or capacity is exceeded.
     * @throws MeasurementException If the measurement cannot be added.
     */
    @Override
    public boolean addMeasurement(Measurement msrmnt, Container cntnr) throws ContainerException, MeasurementException {
        if (hasContainer(cntnr) == false) {
            throw new ContainerException("The container doesnt exit in the aidbox");
        }

        if (findMeasuremet(msrmnt, cntnr) != null) {
            return false;
        }
        if (msrmnt.getValue() > cntnr.getCapacity()) {
            throw new ContainerException("The measurement is higher than the capacity of the container");
        }

        try {
            cntnr.addMeasurement(msrmnt);
        } catch (MeasurementException exc) {
            throw new ContainerException("Can´t add measurement");
        }

        return true;
    }

    /**
     * Retrieves all aid boxes in the institution.
     *
     * @return An array of {@link AidBox}.
     */
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

    /**
     * Retrieves a specific container from an aid box based on the container type.
     *
     * @param aidbox The aid box from which to retrieve the container.
     * @param ct The type of container to retrieve.
     * @return The container if found.
     * @throws ContainerException If the aid box or container does not exist.
     */
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

    /**
     * Finds a specific vehicle in the institution.
     *
     * @param vhcl The vehicle to search for.
     * @return The vehicle if found, otherwise null.
     */
    private Vehicle findVehicle(Vehicle vhcl) {
        for (int i = 0; i < this.nVehicle; i++) {
            if (this.vehicles[i].equals(vhcl)) {
                return vehicles[i];
            }
        }
        return null;
    }

    /**
     * Expands the vehicle array when it reaches maximum capacity.
     */
    private void expandVehicle() {
        Vehicle[] tmpVehicle = new VehicleImp[this.vehicles.length * EXPAND];

        for (int i = 0; i < this.nVehicle; i++) {
            tmpVehicle[i] = this.vehicles[i];
        }
        this.vehicles = tmpVehicle;
    }

    /**
     * Retrieves all vehicles in the institution.
     *
     * @return An array of {@link Vehicle}.
     */
    @Override
    public Vehicle[] getVehicles() {
        Vehicle[] tmpVehicle = new VehicleImp[this.nVehicle];

        for (int i = 0; i < this.nVehicle; i++) {
            tmpVehicle[i] = new VehicleImp((VehicleImp) this.vehicles[i]);
        }

        return tmpVehicle;
    }

    /**
     * Adds a new vehicle to the institution.
     *
     * @param vhcl The vehicle to be added.
     * @return true if the vehicle was successfully added, otherwise false.
     * @throws VehicleException If the vehicle already exists or is null.
     */
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

    /**
     * Disables a specific vehicle in the institution.
     *
     * @param vhcl The vehicle to be disabled.
     * @throws VehicleException If the vehicle is not found or is already disabled.
     */
    @Override
    public void disableVehicle(Vehicle vhcl) throws VehicleException {
        if (findVehicle(vhcl) == null) {
            throw new VehicleException("Vehicle not found");
        }

        VehicleImp myVehicle = (VehicleImp) vhcl;
        if (!myVehicle.isEnable()) {
            throw new VehicleException("The vehicle is already disable");
        }

        myVehicle.setEnable(false);
    }

    /**
     * Enables a specific vehicle in the institution.
     *
     * @param vhcl The vehicle to be enabled.
     * @throws VehicleException If the vehicle is not found or is already enabled.
     */
    @Override
    public void enableVehicle(Vehicle vhcl) throws VehicleException {
        if (findVehicle(vhcl) == null) {
            throw new VehicleException("Vehicle not found");
        }

        VehicleImp myVehicle = (VehicleImp) vhcl;
        if (!myVehicle.isEnable()) {
            throw new VehicleException("The vehicle is already enable");
        }

        myVehicle.setEnable(false);
    }

    /**
     * Finds a specific picking map in the institution.
     *
     * @param pm The picking map to search for.
     * @return The picking map if found, otherwise null.
     */
    private PickingMap findPickingMap(PickingMap pm) {
        for (int i = 0; i < nPickingMap; i++) {
            if (this.pickingMaps[i].equals(pm)) {
                return pickingMaps[i];
            }
        }
        return null;
    }

    /**
     * Expands the picking map array when it reaches maximum capacity.
     */
    private void expandPickingMap() {
        PickingMap[] PMap = new PickingMap[this.pickingMaps.length * EXPAND];

        for (int i = 0; i < this.nPickingMap; i++) {
            PMap[i] = this.pickingMaps[i];
        }
        this.pickingMaps = PMap;
    }

    /**
     * Adds a new picking map to the institution.
     *
     * @param pm The picking map to be added.
     * @return true if the picking map was successfully added, otherwise false.
     * @throws PickingMapException If the picking map is null or already exists.
     */
    @Override
    public boolean addPickingMap(PickingMap pm) throws PickingMapException {
        if (this.nPickingMap == this.pickingMaps.length) {
            expandPickingMap();
        }

        if (pm == null) {
            throw new PickingMapException("The Picking Map is null");
        }

        if (findPickingMap(pm) != null) {
            return false;
        }

        this.pickingMaps[this.nPickingMap++] = pm;
        return true;
    }

    /**
     * Retrieves all picking maps in the institution.
     *
     * @return An array of {@link PickingMap}.
     */
    @Override
    public PickingMap[] getPickingMaps() {
        PickingMap[] Pm = new PickingMap[this.nPickingMap];
        for (int i = 0; i < this.nPickingMap; i++) {
            Pm[i] = this.pickingMaps[i];
        }
        return Pm;
    }

    /**
     * Retrieves all picking maps in the institution within a date range.
     *
     * @param ldt The start date of the range.
     * @param ldt1 The end date of the range.
     * @return An array of {@link PickingMap}.
     */
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

    /**
     * Retrieves the most recent picking map in the institution.
     *
     * @return The most recent picking map.
     * @throws PickingMapException If there are no picking maps in the institution.
     */
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

    /**
     * Retrieves the distance from an aid box to the base location.
     *
     * @param aidbox The aid box for which to calculate the distance.
     * @return The distance to the base location.
     * @throws AidBoxException If the aid box is invalid or not found.
     */
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

    /**
     * Checks if this institution is equal to another object.
     *
     * @param obj The object to compare.
     * @return true if the objects are equal, otherwise false.
     */
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

    /**
     * Expands the report array when it reaches maximum capacity.
     */
    public void expandReports() {
        ReportImp[] newReports = new ReportImp[this.reports.length * 2];
        for (int i = 0; i < this.nReport; i++) {
            newReports[i] = reports[i];
        }
        this.reports = newReports;
    }

    /**
     * Adds a new report to the institution.
     *
     * @param report The report to be added.
     * @return true if the report was successfully added, otherwise false.
     */
    public boolean addReport(Report report) {
        if (report == null) {
            return false;
        }
        if (this.nReport == this.reports.length) {
            expandReports();
        }

        this.reports[this.nReport++] = (ReportImp) report;
        return true;
    }

    /**
     * Retrieves all reports in the institution.
     *
     * @return An array of {@link Report}.
     */
    public Report[] getReports() {
        ReportImp[] reports = new ReportImp[this.nReport];
        for (int i = 0; i < this.nReport; i++) {
            reports[i] = this.reports[i];
        }
        return reports;
    }

    /**
     * Generates a string representation of all AidBoxes contained in this Institution.
     * Each AidBox is converted to its string representation using the toString() method.
     *
     * @return A string containing the representation of all AidBoxes in this Institution.
     */
    private String aidBoxToString() {
        String s = "";

        for (int i = 0; i < this.nAidBox; i++) {
            s += this.aidBoxes[i].toString();
        }
        return s;
    }



    /**
     * Generates a string representation of all Vehicles contained in this Institution.
     * Each Vehicle is converted to its string representation using the toString() method.
     *
     * @return A string containing the representation of all Vehicles in this Institution.
     */
    private String vehicleToString() {
        String s = "";

        for (int i = 0; i < this.nVehicle; i++) {
            s += this.vehicles[i].toString();
        }
        return s;
    }


    /**
     * Returns a string representation of this Institution, including all its AidBoxes
     * and Vehicles. Uses the aidBoxToString() and vehicleToString() methods to obtain
     * string representations of AidBoxes and Vehicles, respectively.
     *
     * @return A string containing the complete representation of this Institution,
     *         including all its AidBoxes and Vehicles.
     */
    @Override
    public String toString() {
        String s = aidBoxToString();
        String s2 = vehicleToString();
        return "\n\nInstitutionInc{" + s + s2 + '}';
    }

}