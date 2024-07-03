/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.ContainerType;

/**
 *
 * @author fabio
 */
public class Capacity {
    
    private ContainerType type;
    private int capacity;

    public Capacity(ContainerType type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    protected ContainerType getType() {
        return type;
    }

    protected int getCapacity() {
        return capacity;
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

        return this.type.equals(((Capacity) obj).type);
    }

    @Override
    public String toString() {
        return "{" + "type=" + type + ", capacity=" + capacity + '}';
    }
}

