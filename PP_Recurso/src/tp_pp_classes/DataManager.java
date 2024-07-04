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

import Alert.Alert;
import Exceptions.AidBoxInArrayException;
import Exceptions.ContainerInArrayException;
import Exceptions.LocationInArrayException;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Vehicle;
import http.HttpProviderImp;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tp_pp_management.Capacity;
import tp_pp_management.VehicleImp;

/**
 * The DataManager class manages various entities such as alerts, containers,
 * aid boxes, locations, measurements, and vehicles, retrieving data from an
 * external API and handling storage and retrieval operations. It uses
 * HttpProviderImp for HTTP operations and includes functionalities for adding,
 * retrieving, and expanding arrays as needed.
 */
public class DataManager {

    /**
     * Array to store alerts.
     */
    private Alert[] alerts;

    /**
     * Maximum size of the alerts array.
     */
    private final int MAX = 4;

    /**
     * Number of alerts currently stored in the array.
     */
    private int numberAlerts;

    /**
     * Array to store containers.
     */
    private Container[] containers;

    /**
     * Array to store aid boxes.
     */
    private AidBox[] aidboxes;

    /**
     * Array to store locations.
     */
    private LocationImp[] locations;

    /**
     * Array to store measurements.
     */
    private Measurement[] measurements;

    /**
     * Array to store vehicles.
     */
    private Vehicle[] vehicles;

    /**
     * Number of containers, aid boxes, locations, measurements and vehicles
     * currently stored in the array.
     */
    private int numberContainers, numberAidBoxes, numberLocations, numberMeasurements, numberVehicles;

    /**
     * Factor by which to expand arrays when they reach capacity.
     */
    private final int EXPAND = 2;

    /**
     * HTTP provider for API communication.
     */
    private HttpProviderImp httpProvider = new HttpProviderImp();

    /**
     * Constructs a DataManager object initializing arrays and retrieving
     * initial data from APIs.
     */
    public DataManager() {

        this.alerts = new Alert[MAX];
        this.numberAlerts = 0;

        try {
            ApiContainers();
        } catch (IOException | ParseException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ApiAidboxes();
        } catch (AidBoxInArrayException | IOException | ParseException | ContainerException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ApiMeasurement();
        } catch (IOException | ParseException | MeasurementException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ApiVehicles();
        } catch (IOException | ParseException | VehicleException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Expands the alerts array when it reaches capacity.
     */
    private void expandAlerts() {
        Alert[] alert = new Alert[alerts.length * EXPAND];
        for (int i = 0; i < numberAlerts; i++) {
            alert[i] = alerts[i];
        }
        alerts = alert;
    }

    /**
     * Adds an alert to the alerts array.
     *
     * @param alert the alert to add
     * @return true if the alert was successfully added, false otherwise
     */
    public boolean addAlert(Alert alert) {
        if (alert == null) {
            return false;
        }
        if (numberAlerts > alerts.length) {
            expandAlerts();
        }
        alerts[numberAlerts++] = alert;
        return true;
    }

    /**
     * Retrieves a copy of the alerts array.
     *
     * @return an array of alerts
     */
    public Alert[] getAlerts() {
        Alert[] copyAlerts = new Alert[numberAlerts];
        for (int i = 0; i < numberAlerts; i++) {
            copyAlerts[i] = new Alert(alerts[i]);
        }
        return copyAlerts;
    }

    /**
     * Retrieves container data from an external API and populates the
     * containers array.
     *
     * @throws IOException if there is an error reading from the API
     * @throws ParseException if there is an error parsing API response
     */
    public void ApiContainers() throws IOException, ParseException {

        String jsonResponse = httpProvider.getContainers();
        JSONParser parser = new JSONParser();
        JSONArray containersArray = (JSONArray) parser.parse(jsonResponse);

        ContainerType[] types = getTypes();

        containers = new ContainerImp[containersArray.size()];

        for (int i = 0; i < containersArray.size(); i++) {
            JSONObject container = (JSONObject) containersArray.get(i);

            String id = (String) container.get("_id");
            String code = (String) container.get("code");
            int capacity = ((Long) container.get("capacity")).intValue();

            String type = (String) container.get("type");
            int j = 0;
            ContainerType containerType = null;
            boolean found = false;
            while (j < types.length && !found) {
                if (types[j].equals(type)) {
                    containerType = types[j];
                    found = true;
                }
                j++;
            }
            if (!found) {
                String alertDescription = "Unknown container type encountered: " + type;
                Alert alert = new Alert(alertDescription, container);
                addAlert(alert);
                continue;
            }
            try {
                addContainerM(new ContainerImp(id, code, capacity, containerType));
            } catch (ContainerInArrayException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves a copy of the containers array.
     *
     * @return an array of containers
     */
    public Container[] getContainers() {
        Container[] result = new ContainerImp[numberContainers];
        for (int i = 0; i < numberContainers; i++) {
            result[i] = containers[i];
        }
        return result;
    }

    /**
     * Adds a container to the containers array.
     *
     * @param cntnr the container to add
     * @return true if the container was successfully added, false otherwise
     * @throws ContainerInArrayException if the container already exists in the
     * array
     */
    public boolean addContainerM(Container cntnr) throws ContainerInArrayException {
        if (cntnr == null) {
            return false;
        }

        for (int i = 0; i < numberContainers; i++) {
            if (containers[i].equals(cntnr)) {
                throw new ContainerInArrayException();
            }
        }

        this.containers[numberContainers++] = cntnr;
        return true;
    }

    /**
     * Retrieves an array of container types from an external API.
     *
     * @return an array of {@link ContainerType} objects.
     * @throws IOException if an I/O error occurs during the API call.
     * @throws ParseException if the response cannot be parsed correctly.
     */
    public ContainerType[] getTypes() throws IOException, ParseException {
        String jsonResponse = httpProvider.getTypes();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

        JSONArray typesArray = (JSONArray) jsonObject.get("types");
        ContainerType[] types = new ContainerType[typesArray.size()];

        for (int i = 0; i < typesArray.size(); i++) {
            String typeName = (String) typesArray.get(i);
            types[i] = new ContainerTypeImp(typeName);
        }

        return types;
    }

    /**
     * Finds a container by its code.
     *
     * @param code the code of the container to find.
     * @return the {@link Container} with the specified code, or null if not
     * found.
     */
    private Container findContainer(String code) {
        if (code == null || containers == null) {
            return null;
        }

        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null && containers[i].getCode().equals(code)) {
                return containers[i];
            }
        }
        return null;
    }

    /**
     * Retrieves and processes aid box data from an external API.
     *
     * @throws IOException if there is an error reading from the API
     * @throws ParseException if there is an error parsing API response
     * @throws ContainerException if a container referenced by an aid box is not
     * found
     * @throws AidBoxInArrayException if the aid box already exists in the array
     */
    public void ApiAidboxes() throws IOException, ParseException, ContainerException, AidBoxInArrayException {
        String jsonResponse = httpProvider.getAidBoxes();
        JSONParser parser = new JSONParser();
        JSONArray AidBoxesArray = (JSONArray) parser.parse(jsonResponse);

        aidboxes = new AidBoxImp[AidBoxesArray.size()];

        for (int i = 0; i < AidBoxesArray.size(); i++) {
            JSONObject aidbox = (JSONObject) AidBoxesArray.get(i);

            String id = (String) aidbox.get("_id");
            String code = (String) aidbox.get("code");
            String zone = (String) aidbox.get("Zona");

            AidBoxImp myAidBox = new AidBoxImp(id, code, zone);

            JSONArray containersFromAidBox = (JSONArray) aidbox.get("containers");

            for (int j = 0; j < containersFromAidBox.size(); j++) {
                String codeContainer = (String) containersFromAidBox.get(j);
                Container container = findContainer(codeContainer);
                if (container != null) {
                    myAidBox.addContainer(container);
                } else {
                    String alertDescription = "Container not found: " + codeContainer;
                    Alert alert = new Alert(alertDescription, aidbox);
                    addAlert(alert);
                }
            }
            ApiLocation(myAidBox);
            addAidBoxM(myAidBox);
        }
    }

    /**
     * Adds a new aid box to the DataManager.
     *
     * @param aid the {@link AidBox} to be added.
     * @return true if the aid box was added successfully, false otherwise.
     * @throws AidBoxInArrayException if the aid box is already present in the
     * array.
     */
    public boolean addAidBoxM(AidBox aid) throws AidBoxInArrayException {
        if (aid == null) {
            return false;
        }

        for (int i = 0; i < numberAidBoxes; i++) {
            if (aidboxes[i].equals(aid)) {
                throw new AidBoxInArrayException();
            }
        }
        this.aidboxes[numberAidBoxes++] = aid;
        return true;
    }

    /**
     * Retrieves a copy of the aid boxes array.
     *
     * @return an array of aid boxes
     */
    public AidBox[] getAidBox() {
        AidBox[] result = new AidBoxImp[aidboxes.length];
        for (int i = 0; i < aidboxes.length; i++) {
            result[i] = aidboxes[i];
        }
        return result;
    }

    /**
     * Retrieves and processes location data from an external API and associates
     * it with an aid box.
     *
     * @param aidBox the aid box to associate locations with
     * @throws IOException if there is an error reading from the API
     * @throws ParseException if there is an error parsing API response
     * @throws ContainerException if a container referenced by location data is
     * not found
     */
    public void ApiLocation(AidBox aidBox) throws IOException, ParseException, ContainerException {
        String jsonResponse = httpProvider.getDistancesAidbox();
        JSONParser parser = new JSONParser();
        JSONArray locationsArray = (JSONArray) parser.parse(jsonResponse);

        for (int i = 0; i < locationsArray.size(); i++) {
            JSONObject location = (JSONObject) locationsArray.get(i);

            String from = (String) location.get("from");
            JSONArray toArray = (JSONArray) location.get("to");

            if (from.equals(aidBox.getCode())) {
                for (int j = 0; j < toArray.size(); j++) {
                    JSONObject toObject = (JSONObject) toArray.get(j);

                    String name = (String) toObject.get("name");
                    double distance = (long) toObject.get("distance");
                    double duration = (long) toObject.get("duration");

                    try {
                        ((AidBoxImp) aidBox).addLocation(new LocationImp(name, distance, duration));
                    } catch (AidBoxException ex) {
                        String errorMessage = "Error adding readings to the AidBox: " + ex.getMessage();
                        Alert alert = new Alert(errorMessage, location);
                        addAlert(alert);
                        Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * Adds a location to the locations array.
     *
     * @param loc the location to add
     * @return true if the location was successfully added, false otherwise
     * @throws LocationInArrayException if the location already exists in the
     * array
     */
    public boolean addLocationM(LocationImp loc) throws LocationInArrayException {
        if (loc == null) {
            return false;
        }

        for (int i = 0; i < numberLocations; i++) {
            if (locations[i].equals(loc)) {
                throw new LocationInArrayException("Location already exists in the array.");
            }
        }

        for (int i = 0; i < numberLocations; i++) {
            if (locations[i].equals(loc)) {
                throw new LocationInArrayException();
            }
        }

        this.locations[numberLocations++] = loc;
        return true;
    }

    /**
     * Retrieves a copy of the locations array.
     *
     * @return an array of locations
     */
    public LocationImp[] getLocations() {
        LocationImp[] result = new LocationImp[numberLocations];
        for (int i = 0; i < numberLocations; i++) {
            result[i] = locations[i];
        }
        return result;
    }

    /**
     * Retrieves and processes measurement data from an external API.
     *
     * @throws IOException if there is an error reading from the API
     * @throws ParseException if there is an error parsing API response
     * @throws MeasurementException if a measurement already exists in the array
     */
    public void ApiMeasurement() throws IOException, ParseException, MeasurementException {
        String jsonResponse = httpProvider.getReadings();
        JSONParser parser = new JSONParser();
        JSONArray measurementsArray = (JSONArray) parser.parse(jsonResponse);

        measurements = new MeasurementImp[measurementsArray.size()];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        for (int i = 0; i < measurementsArray.size(); i++) {
            JSONObject measurement = (JSONObject) measurementsArray.get(i);

            String contentor = (String) measurement.get("contentor");
            String dateString = (String) measurement.get("data");
            long valor = (long) measurement.get("valor");

            LocalDateTime data = LocalDateTime.parse(dateString, formatter);

            addMeasurementM(new MeasurementImp(contentor, data, valor));
        }
    }

    /**
     * Adds a measurement to the measurements array.
     *
     * @param msrnt the measurement to add
     * @return true if the measurement was successfully added, false otherwise
     * @throws MeasurementException if the measurement already exists in the
     * array
     */
    public boolean addMeasurementM(MeasurementImp msrnt) throws MeasurementException {
        if (msrnt == null) {
            return false;
        }
        for (int i = 0; i < numberMeasurements; i++) {
            if (measurements[i].equals(msrnt)) {
                throw new MeasurementException();
            }
        }
        this.measurements[numberMeasurements++] = msrnt;
        return true;
    }

    /**
     * Returns an array of measurements currently stored in the DataManager.
     *
     * @return an array of {@link Measurement} objects.
     */
    public Measurement[] getMeasurement() {
        Measurement[] result = new Measurement[numberMeasurements];
        for (int i = 0; i < numberMeasurements; i++) {
            result[i] = measurements[i];
        }
        return result;
    }

    /**
     * Expands the vehicles array when it reaches capacity.
     */
    private void expand() {
        Vehicle[] tmpCap = new VehicleImp[this.vehicles.length * EXPAND];

        for (int i = 0; i < this.numberVehicles; i++) {
            tmpCap[i] = this.vehicles[i];
        }
        this.vehicles = tmpCap;
    }

    /**
     * Retrieves and processes vehicle data from an external API.
     *
     * @throws IOException if there is an error reading from the API
     * @throws ParseException if there is an error parsing API response
     * @throws VehicleException if a vehicle already exists in the array
     */
    public void ApiVehicles() throws IOException, ParseException, VehicleException {
        String jsonResponse = httpProvider.getVehicles();
        JSONParser parser = new JSONParser();
        JSONArray vehiclesArray = (JSONArray) parser.parse(jsonResponse);

        this.vehicles = new VehicleImp[vehiclesArray.size()];

        for (int i = 0; i < vehiclesArray.size(); i++) {
            JSONObject vehicle = (JSONObject) vehiclesArray.get(i);

            String code = (String) vehicle.get("code");
            JSONObject capacityObject = (JSONObject) vehicle.get("capacity");

            VehicleImp v = new VehicleImp(code);

            for (Object key : capacityObject.keySet()) {
                if (key != null) {
                    String type = (String) key;
                    int capacity = ((Long) capacityObject.get(type)).intValue();
                    ContainerType containerType = new ContainerTypeImp(type);
                    Capacity cap = new Capacity(containerType, capacity);
                    try {
                        v.addCapacity(cap);
                    } catch (VehicleException ex) {
                        String errorMessage = "Error adding capacity to the vehicle " + v.getCode() + ": " + ex.getMessage();
                        Alert alert = new Alert(errorMessage, vehicle);
                        addAlert(alert);
                        Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            try {
                addVehiclesM(v);
            } catch (MeasurementException ex) {
                String errorMessage = "Error adding vehicle " + v.getCode() + " to DataManager: " + ex.getMessage();
                Alert alert = new Alert(errorMessage, capacityObject);
                addAlert(alert);
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Adds a vehicle to the vehicles array.
     *
     * @param vhcl the vehicle to add
     * @return true if the vehicle was successfully added, false otherwise
     * @throws MeasurementException if the vehicle already exists in the array
     */
    public boolean addVehiclesM(VehicleImp vhcl) throws MeasurementException {
        if (vhcl == null) {
            return false;
        }
        for (int i = 0; i < numberVehicles; i++) {
            if (vehicles[i].equals(vhcl)) {
                throw new MeasurementException();
            }
        }

        if (numberVehicles > vehicles.length) {
            expand();
        }

        this.vehicles[numberVehicles++] = vhcl;
        return true;
    }

    /**
     * Retrieves a copy of the vehicles array.
     *
     * @return an array of vehicles
     */
    public Vehicle[] getVehicles() {
        Vehicle[] result = new VehicleImp[numberVehicles];
        for (int i = 0; i < numberVehicles; i++) {
            result[i] = vehicles[i];
        }
        return result;
    }
}
