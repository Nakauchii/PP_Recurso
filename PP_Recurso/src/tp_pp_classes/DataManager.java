/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

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

/**
 *
 * @author fabio
 */
public class DataManager {

    private Container[] containers;
    private AidBox[] aidboxes;
    private LocationImp[] locations;
    private Measurement[] measurements;
    private int numberContainers, numberAidBoxes, numberLocations, numberMeasurements;
    private final int EXPAND = 2;
    private HttpProviderImp httpProvider = new HttpProviderImp();

    public DataManager() {
        try {
            ApiContainers();
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try {
                ApiAidboxes();
            } catch (AidBoxInArrayException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ContainerException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ApiMeasurement();
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MeasurementException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                throw new ParseException(1);
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
                }
            }
            ApiLocation(myAidBox);
            addAidBoxM(myAidBox);
        }
    }

    private AidBox findAidBox(String code) {
        if (code == null || aidboxes == null) {
            return null;
        }

        for (int i = 0; i < aidboxes.length; i++) {
            if (aidboxes[i] != null && aidboxes[i].getCode().equals(code)) {
                return aidboxes[i];
            }
        }
        return null;
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
        if (numberMeasurements >= measurements.length) {
            expandMeasurementM();
        }
        this.measurements[numberMeasurements++] = msrnt;
        return true;
    }

    private void expandMeasurementM() {
        Measurement[] newMeasurements = new Measurement[this.measurements.length * EXPAND];

        for (int i = 0; i < this.numberMeasurements; i++) {
            newMeasurements[i] = this.measurements[i];
        }
        this.measurements = newMeasurements;
    }

    public Measurement[] getMeasurement() {
        Measurement[] result = new Measurement[numberMeasurements];
        for (int i = 0; i < numberMeasurements; i++) {
            result[i] = measurements[i];
        }
        return result;
    }
}
