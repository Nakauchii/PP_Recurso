/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import Exceptions.ContainerInArrayException;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.ContainerException;
import http.HttpProviderImp;
import java.io.IOException;
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
    private HttpProviderImp httpProvider = new HttpProviderImp();

    public Container[] ApiContainers() throws IOException, ParseException {

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
                } else {
                    throw new ParseException(1);
                }
            }
            try {
                addContainerM(new ContainerImp(id, code, capacity, containerType));
            } catch (ContainerInArrayException e) {

            }
        }
        return containers;
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
            System.out.println(types[i]);
        }

        return types;
    }

    public Container findContainer(String code) {
        if (code == null) {
            return null;
        }

        for (int i = 0; i < containers.length; i++) {
            if (containers[i].getCode().equals(code)) {
                return containers[i];
            }
        }
        return null;
    }

    public AidBox[] ApiAidboxes() throws IOException, ParseException, ContainerException {

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
                String codeContainer = (String) containersFromAidBox.get(i);
                myAidBox.addContainer(findContainer(codeContainer));
            }
            aidboxes[i] = myAidBox;
        }
        return aidboxes;
    }

}
