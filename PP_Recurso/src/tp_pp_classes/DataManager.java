/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 *
 * @author fabio
 */
public class DataManager {

    private Alert[] alerts;
    private final int MAX = 4;
    private int numberAlerts;

    private Container[] containers;
    private AidBox[] aidboxes;
    private LocationImp[] locations;
    private Measurement[] measurements;
    private Vehicle[] vehicles;
    private int numberContainers, numberAidBoxes, numberLocations, numberMeasurements, numberVehicles;
    private final int EXPAND = 2;
    private HttpProviderImp httpProvider = new HttpProviderImp();

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

    private void expandAlerts(){
        Alert[] alert = new Alert[alerts.length * EXPAND];
        for(int i = 0; i < numberAlerts; i++) {
            alert[i] = alerts[i];
        }
        alerts = alert;
    }

    public boolean addAlert(Alert alert){
        if (alert == null){
            return false;
        }
        if (numberAlerts > alerts.length){
            expandAlerts();
        }
        alerts[numberAlerts++] = alert;
        return true;
    }

    public Alert[] getAlerts() {
        Alert[] copyAlerts = new Alert[numberAlerts];
        for(int i = 0; i < numberAlerts; i++) {
            copyAlerts[i] = new Alert(alerts[i]);
        }
        return copyAlerts;
    }

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

    public Container[] getContainers() {
        Container[] result = new ContainerImp[numberContainers];
        for (int i = 0; i < numberContainers; i++) {
            result[i] = containers[i];
        }
        return result;
    }

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
                }else {
                    String alertDescription = "Container not found: " + codeContainer;
                    Alert alert = new Alert(alertDescription, aidbox);
                    addAlert(alert);
                }
            }
            ApiLocation(myAidBox);
            addAidBoxM(myAidBox);
        }
    }

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

    public AidBox[] getAidBox() {
        AidBox[] result = new AidBoxImp[aidboxes.length];
        for (int i = 0; i < aidboxes.length; i++) {
            result[i] = aidboxes[i];
        }
        return result;
    }

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

    public LocationImp[] getLocations() {
        LocationImp[] result = new LocationImp[numberLocations];
        for (int i = 0; i < numberLocations; i++) {
            result[i] = locations[i];
        }
        return result;
    }

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

    public Measurement[] getMeasurement() {
        Measurement[] result = new Measurement[numberMeasurements];
        for (int i = 0; i < numberMeasurements; i++) {
            result[i] = measurements[i];
        }
        return result;
    }
    
    private void expand() {
        Vehicle[] tmpCap = new VehicleImp[this.vehicles.length * EXPAND];

        for (int i = 0; i < this.numberVehicles; i++) {
            tmpCap[i] = this.vehicles[i];
        }
        this.vehicles = tmpCap;
    }

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
                if(key != null){
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


    public boolean addVehiclesM(VehicleImp vhcl) throws MeasurementException {
        if (vhcl == null) {
            return false;
        }
        for (int i = 0; i < numberVehicles; i++) {
            if (vehicles[i].equals(vhcl)) {
                throw new MeasurementException();
            }
        }
        
        if(numberVehicles > vehicles.length){
            expand();
        }
        
        this.vehicles[numberVehicles++] = vhcl;
        return true;
    }

    public Vehicle[] getVehicles() {
        Vehicle[] result = new VehicleImp[numberVehicles];
        for (int i = 0; i < numberVehicles; i++) {
            result[i] = vehicles[i];
        }
        return result;
    }
}
