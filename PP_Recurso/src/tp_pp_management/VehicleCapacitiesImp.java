/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.ContainerType;

/**
 *
 * @author Roger Nakauchi
 */
public class VehicleCapacitiesImp {
    private ContainerType[] containerTypes;
    private double[] capacities;
    private int size;
    
    public VehicleCapacitiesImp(int initialCapacity) {
        this.containerTypes = new ContainerType[initialCapacity];
        this.capacities = new double[initialCapacity];
        this.size = 0;
    }
    
    //Add uma nova capacidade pra um tipo de container
    public void addCapacity(ContainerType containerType, double capacity) {
        if(size == containerTypes.length) {
            expandCapacity();
        }
        containerTypes[size] = containerType;
        capacities[size] = capacity;
        size++;
    }
    
    public void expandCapacity() {
        int newCapacity = containerTypes.length * 2;
        ContainerType[] newContainerTypes = new ContainerType[newCapacity];
        double[] newCapacities = new double[newCapacity];
        
        for(int i = 0; i < size; i++) {
            newContainerTypes[i] = containerTypes[i];
            newCapacities[i] = capacities[i];
        }
        
        containerTypes = newContainerTypes;
        capacities = newCapacities;
    }
    
    
    public double getCapacity(ContainerType containerType) {
        for(int i = 0; i < size; i++) {
            if(containerTypes[i].equals(containerType)) {
                return capacities[i];
            }
        }
        return 0;
    }
    
    
}
