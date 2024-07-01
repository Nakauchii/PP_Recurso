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

    public AidBoxImp(String id, String code, String zone) {
        this.id = id;
        this.code = code;
        this.zone = zone;
        this.containers = new ContainerImp[4];
        this.numberContainers = 0;
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getZone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Container[] getContainers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void removeContainer(Container cntnr) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return "AidBoxImp{" + "id=" + id + ", code=" + code + ", zone=" + zone + ", containers=" + containers + '}';
    }

    

}
