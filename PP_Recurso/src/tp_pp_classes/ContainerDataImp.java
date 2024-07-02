/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import com.estg.core.Container;

/**
 *
 * @author Roger Nakauchi
 */
public class ContainerDataImp {

    private Container[] pickedContainers;
    private Container[] nonPickedContainers;
    private int pickedCount;
    private int nonPickedCount;

    public ContainerDataImp(int initialCapacity) {
        this.pickedContainers = new Container[initialCapacity];
        this.nonPickedContainers = new Container[initialCapacity];
        this.pickedCount = 0;
        this.nonPickedCount = 0;
    }

    public void addPickedContainer(Container container) {
        if (pickedCount == pickedContainers.length) {
            expandPickedContainers();
        }
        pickedContainers[pickedCount++] = container;
    }

    public void addNonPickedContainer(Container container) {
        if (nonPickedCount == nonPickedContainers.length) {
            expandNonPickedContainers();
        }
        nonPickedContainers[nonPickedCount++] = container;
    }

    public void expandPickedContainers() {
        Container[] newContainers = new Container[pickedContainers.length * 2];
        for (int i = 0; i < pickedContainers.length; i++) {
            newContainers[i] = pickedContainers[i];
        }
        pickedContainers = newContainers;
    }

    public void expandNonPickedContainers() {
        Container[] newContainers = new Container[nonPickedContainers.length * 2];
        for (int i = 0; i < nonPickedContainers.length; i++) {
            newContainers[i] = nonPickedContainers[i];
        }
        nonPickedContainers = newContainers;
    }

    public int getPickedCount() {
        return pickedCount;
    }

    public int getNonPickedCount() {
        return nonPickedCount;
    }

}
