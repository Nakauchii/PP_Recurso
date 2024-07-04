/*
 * Name: Roger Nakauchi
 * Number: 8210005
 * Class: LSIRCT1
 *
 * Name: Fábio da Cunha
 * Number: 8210619
 * Class: LSIRCT1
 */
package tp_pp_management;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_pp_classes.AidBoxImp;
import tp_pp_classes.DataManager;

/**
 * Implementation of the Route interface.
 *
 * This class manages a route consisting of multiple aid boxes that a vehicle
 * can pick up. It includes methods to add, remove, and manage aid boxes within
 * the route.
 */
public class RouteImp implements com.estg.pickingManagement.Route {

    /**
     * Array to store the aid boxes in the route.
     */
    private AidBox[] aidBoxes;

    /**
     * Number of aid boxes currently in the route.
     */
    private int numberOfAidBoxes;

    /**
     * The vehicle assigned to this route.
     */
    private Vehicle vehicle;

    /**
     * The report associated with this route.
     */
    private Report report;

    /**
     * The data manager providing access to necessary data.
     */
    private DataManager dataManager;

    /**
     * Constant used to determine the expansion factor of the aidBoxes array.
     */
    private final int EXPAND = 2;

    /**
     * Initial capacity of the aidBoxes array.
     */
    private static final int INITIAL_CAPACITY = 10;

    /**
     * Constructs a new RouteImp with the specified vehicle.
     *
     * @param vehicle The vehicle assigned to this route.
     */
    public RouteImp(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.aidBoxes = new AidBox[INITIAL_CAPACITY];
        this.numberOfAidBoxes = 0;
    }

    /**
     * Expands the aidBoxes array when it reaches its capacity.
     */
    public void expandAidBoxesArray() {
        AidBox[] newAidboxes = new AidBox[aidBoxes.length * EXPAND];
        for (int i = 0; i < numberOfAidBoxes; i++) {
            newAidboxes[i] = aidBoxes[i];
        }
        aidBoxes = newAidboxes;
    }

    /**
     * Checks if the vehicle can pick the specified aid box.
     *
     * @param aidbox The aid box to check.
     * @return true if the vehicle can pick the aid box, false otherwise.
     */
    private boolean canVehiclePickAidbox(AidBox aidbox) {
        Container[] containers = aidbox.getContainers();
        for (int i = 0; i < containers.length; i++) {
            if (((VehicleImp) vehicle).canPick(containers[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the specified aid box in the route.
     *
     * @param aid The aid box to find.
     * @return The aid box if found, null otherwise.
     */
    private AidBox findAidBox(AidBox aid) {
        for (int i = 0; i < numberOfAidBoxes; i++) {
            if (aidBoxes[i].equals(aid)) {
                return aidBoxes[i];
            }
        }
        return null;
    }

    /**
     * Adds the specified aid box to the route.
     *
     * @param aidBox The aid box to add.
     * @throws RouteException if the aid box is null, already in the route, or
     * cannot be picked by the vehicle.
     */
    @Override
    public void addAidBox(AidBox aidBox) throws RouteException {
        if (aidBox == null) {
            throw new RouteException();
        }
        if (findAidBox(aidBox) != null) {
            throw new RouteException("AidBox already in the route");
        }
        if (!canVehiclePickAidbox(aidBox)) {
            throw new RouteException();
        }
        if (numberOfAidBoxes >= aidBoxes.length) {
            expandAidBoxesArray();
        }
        aidBoxes[numberOfAidBoxes++] = aidBox;
    }

    /**
     * Removes the specified aid box from the route.
     *
     * @param aidBox The aid box to remove.
     * @return The removed aid box.
     * @throws RouteException if the aid box is null or not found in the route.
     */
    @Override
    public AidBox removeAidBox(AidBox aidBox) throws RouteException {
        if (aidBox == null) {
            throw new RouteException();
        }

        AidBox found = findAidBox(aidBox);

        if (found != null) {
            int index = -1;

            for (int i = 0; i < numberOfAidBoxes; i++) {
                if (aidBoxes[i].equals(aidBox)) {
                    index = i;
                }
            }
            if (index != -1) {
                AidBox removedAidbox = aidBoxes[index];

                for (int j = 0; j < numberOfAidBoxes - 1; j++) {
                    aidBoxes[j] = aidBoxes[j + 1];
                }
                aidBoxes[--numberOfAidBoxes] = null;
                return removedAidbox;
            }
        }
        throw new RouteException();
    }

    /**
     * Checks if the route contains the specified aid box.
     *
     * @param aidBox The aid box to check.
     * @return true if the route contains the aid box, false otherwise.
     */
    @Override
    public boolean containsAidBox(AidBox aidBox) {
        return findAidBox(aidBox) != null;
    }

    /**
     * Replaces an aid box in the route with another aid box.
     *
     * @param aidBox The aid box to replace.
     * @param aidBox1 The new aid box.
     * @throws RouteException if either aid box is null, the old aid box is not
     * in the route, the new aid box is already in the route, or the vehicle
     * cannot pick the new aid box.
     */
    @Override
    public void replaceAidBox(AidBox aidBox, AidBox aidBox1) throws RouteException {
        if (aidBox == null || aidBox1 == null) {
            throw new RouteException();
        }
        if (!containsAidBox(aidBox)) {
            throw new RouteException();
        }
        if (containsAidBox(aidBox1)) {
            throw new RouteException();
        }
        if (!canVehiclePickAidbox(aidBox1)) {
            throw new RouteException();
        }
        for (int i = 0; i < numberOfAidBoxes; i++) {
            if (aidBoxes[i].equals(aidBox)) {
                aidBoxes[i] = aidBox1;
                return;
            }
        }
    }

    /**
     * Inserts an aid box into the route after the specified aid box.
     *
     * @param aidBox The aid box after which the new aid box is inserted.
     * @param aidBox1 The new aid box to insert.
     * @throws RouteException if either aid box is null, the old aid box is not
     * in the route, the new aid box is already in the route, or the vehicle
     * cannot pick the new aid box.
     */
    @Override
    public void insertAfter(AidBox aidBox, AidBox aidBox1) throws RouteException {
        if (aidBox == null || aidBox1 == null) {
            throw new RouteException();
        }
        if (!containsAidBox(aidBox)) {
            throw new RouteException();
        }
        if (containsAidBox(aidBox1)) {
            throw new RouteException();
        }
        if (!canVehiclePickAidbox(aidBox1)) {
            throw new RouteException();
        }
        if (numberOfAidBoxes >= aidBoxes.length) {
            expandAidBoxesArray();
        }

        for (int i = 0; i < numberOfAidBoxes; i++) {
            if (aidBoxes[i].equals(aidBox)) {
                for (int j = numberOfAidBoxes; j > i + 1; j--) {
                    aidBoxes[j] = aidBoxes[j - 1];
                }
                aidBoxes[i + 1] = aidBox1;
                numberOfAidBoxes++;
            }
        }
        throw new RouteException();
    }

    /**
     * Gets the route as an array of aid boxes.
     *
     * @return A copy of the array of aid boxes in the route.
     */
    @Override
    public AidBox[] getRoute() {
        AidBox[] copyRoutes = new AidBox[numberOfAidBoxes];
        for (int i = 0; i < numberOfAidBoxes; i++) {
            copyRoutes[i] = new AidBoxImp((AidBoxImp) aidBoxes[i]);
        }
        return copyRoutes;
    }

    /**
     * Gets the vehicle assigned to this route.
     *
     * @return The vehicle assigned to this route.
     */
    @Override
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    /**
     * Gets the total distance of the route.
     *
     * @return The total distance of the route.
     */
    @Override
    public double getTotalDistance() {
        double totalDistance = 0;
        for (int i = 0; i < numberOfAidBoxes; i++) {
            try {
                totalDistance += aidBoxes[i].getDistance(aidBoxes[i + 1]);
            } catch (AidBoxException ex) {
                Logger.getLogger(RouteImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalDistance;
    }

    /**
     * Gets the total duration of the route.
     *
     * @return The total duration of the route.
     */
    @Override
    public double getTotalDuration() {
        double totalDuration = 0;
        for (int i = 0; i < numberOfAidBoxes; i++) {
            try {
                totalDuration += aidBoxes[i].getDuration(aidBoxes[i + 1]);
            } catch (AidBoxException ex) {
                Logger.getLogger(RouteImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return totalDuration;
    }

    /**
     * Gets the report associated with this route.
     *
     * @return The report associated with this route.
     */
    @Override
    public Report getReport() {
        return this.report;
    }
}
