/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Vehicle;

/**
 *
 * @author Roger Nakauchi
 */
public class VehicleImp implements Vehicle {

    private static final int MAX_OBJECT = 10;
    private static final int EXPANTION_RATE = 2;

    private int nCapacity;
    private String code;
    private Capacity[] capacity;
    private boolean isEnabled;

   
    public VehicleImp(String code) {
        this.code = code;
        this.capacity = new Capacity[MAX_OBJECT];
    }

    private int findCapacity(Capacity cpct) {
        for (int i = 0; i < this.nCapacity; i++) {
            if ((this.capacity[i]).equals(cpct)) {
                return i;
            }
        }
        return -1;
    }

    private void expand() {
        Capacity[] tmpCap = new Capacity[this.capacity.length * EXPANTION_RATE];

        for (int i = 0; i < this.nCapacity; i++) {
            tmpCap[i] = this.capacity[i];
        }
        this.capacity = tmpCap;
    }

    public boolean addCapacity(Capacity cpct) throws VehicleException {

        if (this.nCapacity == this.capacity.length) {
            expand();
        }

        if (cpct == null) {
            throw new VehicleException("Container inválido");
        }

        if (findCapacity(cpct) != -1) {
            return false;
        }

        this.capacity[this.nCapacity++] = cpct;
        return true;
    }

    @Override
    public double getCapacity(ContainerType ct) {

        for (int i = 0; i < this.nCapacity; i++) {
            if (this.capacity[i].getType().equals(ct)) {
                return capacity[i].getCapacity();
            }
        }

        return -1;
    }

    public void setEnable(boolean enable) {
        this.isEnabled = enable;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public boolean isEnable() {
        return this.isEnabled;
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

        return this.code.equals(((VehicleImp) obj).code);
    }

    private String capacityToString() {
        int nCont = 0;
        String s = " ";

        for (Capacity cpct : this.capacity) {
            if (this.capacity[nCont] != null) {
                s += "\n" + this.capacity[nCont++].toString();
            }
        }
        return s;
    }

    @Override
    public String toString() {
        String s = capacityToString();
        return "\n\nVehicleImp{" + ", code=" + code + s + '}';
    }
}
