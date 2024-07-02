/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import Exceptions.AidBoxInArrayException;
import Exceptions.ContainerInArrayException;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.ContainerException;
import http.HttpProviderImp;
import java.io.IOException;
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
    private int numberContainers, numberAidBoxes;
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

    private void expandContainerM() {
        Container[] container = new ContainerImp[this.containers.length * EXPAND];

        for (int i = 0; i < this.numberContainers; i++) {
            containers[i] = this.containers[i];
        }
        this.containers = containers;
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
        if (numberContainers >= containers.length) {
            expandContainerM();
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

        for (int i = 0; i < containers.length; i++) {
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

    private void expandAidBoxM() {
        AidBox[] aidBox = new AidBoxImp[this.aidboxes.length * EXPAND];

        for (int i = 0; i < this.numberAidBoxes; i++) {
            aidboxes[i] = this.aidboxes[i];
        }
        this.aidboxes = aidboxes;
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
        if (numberAidBoxes >= aidboxes.length) {
            expandAidBoxM();
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
}
