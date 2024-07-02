/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;

/**
 *
 * @author fabio
 */
public class AidBoxImp implements AidBox {

    private String id;
    private String code;
    private String zone;
    private Container[] containers;
    private int numberContainers;

    private static String[] aidBoxCodes = new String[10]; //Pra armazenar os codigos das aidboxes
    private static double[] distances = new double[10]; // Array de distancias;
    private static int numberAidboxes = 0;

    public AidBoxImp(String id, String code, String zone) {
        this.id = id;
        this.code = code;
        this.zone = zone;
        this.containers = new ContainerImp[4];
        this.numberContainers = 0;

        //Verifica se o code ja existe no array e add, se não existir
        addAidBoxCode(code);
    }

    public void addAidBoxCode(String code) {
        boolean exist = false;

        for (int i = 0; i < numberAidboxes; i++) {
            if (aidBoxCodes[i].equals(code)) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            //Dobra o tamanho do array de codigos
            if (numberAidboxes == aidBoxCodes.length) {
                String[] newAidBoxCodes = new String[aidBoxCodes.length * 2];
                for (int i = 0; i < aidBoxCodes.length; i++) {
                    newAidBoxCodes[i] = aidBoxCodes[i];
                }
                aidBoxCodes = newAidBoxCodes;

                //Dobra o tamanho do array de distancias
                double[] newDistances = new double[distances.length * 2];
                for (int i = 0; i < distances.length; i++) {
                    newDistances[i] = distances[i];

                }
                distances = newDistances;
            }
            aidBoxCodes[numberAidboxes] = code;
            numberAidboxes++;
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getZone() {
        return this.zone;
    }

    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("AidBox can't be null");
        }

        String targetCode = aidbox.getCode();
        int index = -1;
        int index2 = -1;

        for (int i = 0; i < numberAidboxes; i++) {
            if (aidBoxCodes[i].equals(this.code)) {
                index = i;
            }
            if (aidBoxCodes[i].equals(targetCode)) {
                index2 = i;
            }
        }

        if (index == -1 || index2 == -1) {
            throw new AidBoxException("One or both aidboxes don't exist in the distance matrix");
        }

        return distances[index2];
    }

    @Override
    public double getDuration(AidBox aidbox) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addContainer(Container cntnr) throws ContainerException {
        if (cntnr == null) {
            throw new ContainerException("Container cannot be null");
        }

        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null && containers[i].getType().equals(cntnr.getType())) {
                throw new ContainerException("AidBox already has a container of type: " + cntnr.getType());
            }
        }

        if (numberContainers < containers.length) {
            containers[numberContainers++] = cntnr;
            return true;
        } else {
            throw new ContainerException("Cannot add more containers. AidBox is full.");
        }
    }

    @Override
    public Container getContainer(ContainerType ct) {
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i].getType().equals(ct)) {
                return containers[i];
            }
        }
        return null;
    }

    @Override
    public Container[] getContainers() {
        Container[] result = new Container[numberContainers];
        for (int i = 0; i < numberContainers; i++) {
            result[i] = containers[i];
        }
        return result;
    }

    @Override
    public void removeContainer(Container cntnr) throws AidBoxException {
        if (cntnr == null) {
            throw new AidBoxException("Container is null.");
        }

        boolean found = false;
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i].equals(cntnr)) {
                found = true;
                containers[i] = containers[--numberContainers];
                containers[numberContainers] = null;
                break;
            }
        }

        if (!found) {
            throw new AidBoxException("Container does not exist.");
        }
    }

    @Override
    public String toString() {
        String result = "Code: " + code + "\n"
                + "Zone: " + zone + "\n"
                + "Containers:\n";

        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null) {
                result += containers[i].toString() + "\n";
            }
        }

        return result;
    }

}
