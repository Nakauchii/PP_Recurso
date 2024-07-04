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
import java.lang.module.FindException;
import java.util.Objects;
import tp_pp_classes.LocationImp;

/**
 *
 * @author fabio
 */
public class AidBoxImp implements AidBox {

    private String id;
    private String code;
    private String zone;
    private LocationImp[] locations;
    private Container[] containers;
    private int numberContainers;
    private final int MAX = 4;
    private final int EXPAND = 2;
    private int nLocations;

    public AidBoxImp(String id, String code, String zone) {
        this.id = id;
        this.code = code;
        this.zone = zone;
        this.containers = new ContainerImp[MAX];
        this.locations = new LocationImp[MAX];
        this.numberContainers = 0;
    }
    
    //Construtor para fazer a cópia
    public AidBoxImp(AidBox other) {
        this.id = id;
        this.code = code;
        this.zone = zone;
        this.containers = new ContainerImp[MAX];
        this.locations = new LocationImp[MAX];
        this.numberContainers = 0;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getZone() {
        return this.zone;
    }

    public LocationImp getLocation(String code) {
        for (int i = 0; i < this.nLocations; i++) {
            if (this.locations[i].getCode().equals(code)) {
                return locations[i];
            }
        }
        return null;
    }

    private LocationImp findLocation(LocationImp loc) {
        for (int i = 0; i < this.nLocations; i++) {
            if (this.locations[i].equals(loc)) {
                return locations[i];
            }
        }
        return null;
    }

    private void expandLocation() {
        LocationImp[] location = new LocationImp[this.locations.length * EXPAND];

        for (int i = 0; i < this.nLocations; i++) {
            location[i] = this.locations[i];
        }
        this.locations = location;
    }

    public boolean addLocation(LocationImp loc) throws AidBoxException {
        if (loc == null) {
            return false;
        }

        if (findLocation(loc) != null) {
            return false;
        }

        if (nLocations >= locations.length) {
            expandLocation();
        }

        this.locations[nLocations++] = loc;
        return true;
    }

    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("Aid Box is Invalid");
        }

        LocationImp location = getLocation(aidbox.getCode());
        if (location == null) {
            throw new AidBoxException("Aid Box not found");
        }

        return location.getDistance();
    }

    @Override
    public double getDuration(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("Aid Box is Invalid");
        }
        
        String aidBoxCode = aidbox.getCode();
        LocationImp location = getLocation(aidBoxCode);
        if (location == null) {
            throw new AidBoxException("Aid Box not found");
        }

        return location.getDuration();
    }

    private Container findContainer(Container cntnr) {
        for (int i = 0; i < this.numberContainers; i++) {
            if (this.containers[i].equals(cntnr)) {
                return containers[i];
            }
        }
        return null;
    }

    @Override
    public boolean addContainer(Container cntnr) throws ContainerException {
        if (cntnr == null) {
            throw new ContainerException("Container cannot be null");
        }

        if (findContainer(cntnr) != null) {
            return false;
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

        result += "Locations:\n";

        for (int i = 0; i < nLocations; i++) {
            if (locations[i] != null) {
                result += locations[i].toString() + "\n";
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AidBoxImp other = (AidBoxImp) obj;
        return Objects.equals(this.code, other.code);
    }

}
