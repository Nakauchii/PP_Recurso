/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.ContainerType;
import com.estg.pickingManagement.Vehicle;

/**
 *
 * @author Roger Nakauchi
 */
public class VehicleImp implements Vehicle {
    
    private String code;
    private ContainerType[] containerTypes;
    private double[] capacities;
    private int numberContainerTypes;
    
    public VehicleImp(String code, int numberContainerTypes) {
        this.code = code;
        this.containerTypes = new ContainerType[numberContainerTypes];
        this.capacities = new double[numberContainerTypes];
    }

    @Override
    public String getCode() {
        return this.code;
    }

    
    //Verificar se é isso mesmo, pq não entendi o javadoc
    @Override
    public double getCapacity(ContainerType ct) {
        for(int i = 0; i < numberContainerTypes; i++) {
            if(containerTypes[i] != null && containerTypes[i].equals(ct)) {
                return capacities[i];
            }
        }
        return 0;
    }
    
}
