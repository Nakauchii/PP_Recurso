/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package tp_pp_management;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import java.time.LocalDateTime;
import tp_pp_classes.ContainerImp;
import tp_pp_classes.DataManager;
import tp_pp_classes.LocationImp;

/**
 * Implementation of the Report interface.
 *
 * This class represents a report generated based on a specific route. It
 * calculates and stores various metrics such as the number of used vehicles,
 * picked containers, total distance, total duration, non-picked containers, and
 * not used vehicles.
 */
public class ReportImp implements com.estg.pickingManagement.Report {

    /**
     * The date when the report was generated.
     */
    private LocalDateTime date;

    /**
     * The number of vehicles used in the route.
     */
    private int usedVehicles;

    /**
     * The number of containers picked during the route.
     */
    private int pickedContainers;

    /**
     * The total distance covered during the route.
     */
    private double totalDistance;

    /**
     * The total duration of the route.
     */
    private double totalDuration;

    /**
     * The number of containers that were not picked during the route.
     */
    private int nonPickedContainers;

    /**
     * The number of vehicles that were not used during the route.
     */
    private int notUsedVehicles;

    /**
     * The route for which this report is generated.
     */
    private Route route;

    /**
     * Constructs a new ReportImp instance for the specified route and generates
     * the report.
     *
     * @param route The route for which the report is generated.
     */
    public ReportImp(Route route) {
        this.route = route;
        this.date = LocalDateTime.now();
        generatedReport();
    }

    /**
     * Generates the report by calculating all the required metrics.
     */
    public void generatedReport() {
        this.usedVehicles = calculateUsedVehicles();
        this.pickedContainers = calculatePickedContainers();
        this.totalDistance = calculateTotalDistance();
        this.totalDuration = calculateTotalDuration();
        this.nonPickedContainers = calculateNonPickedContainers();
        this.notUsedVehicles = calculateNotUsedVehicles();
    }

    /**
     * Calculates the number of used vehicles in the route.
     *
     * @return The number of used vehicles.
     */
    private int calculateUsedVehicles() {
        Institution[] institutions = (Institution[]) route.getRoute();
        int count = 0;
        for (int i = 0; i < institutions.length; i++) {
            Vehicle[] vehicles = institutions[i].getVehicles();
            for (int j = 0; j < vehicles.length; j++) {
                if (((VehicleImp) vehicles[j]).isEnable()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calculates the number of picked containers in the route.
     *
     * @return The number of picked containers.
     */
    public int calculatePickedContainers() {
        AidBox[] aidboxes = route.getRoute();
        int count = 0;
        for (int i = 0; i < aidboxes.length; i++) {
            Container[] containers = aidboxes[i].getContainers();
            for (int j = 0; j < containers.length; j++) {
                if (((ContainerImp) containers[j]).isPicked()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calculates the total distance covered during the route.
     *
     * @return The total distance covered.
     */
    public double calculateTotalDistance() {
        double totalDistance = 0;
        AidBox[] aidboxes = route.getRoute();
        for (int i = 0; i < aidboxes.length - 1; i++) {
            try {
                totalDistance += aidboxes[i].getDistance(aidboxes[i + 1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalDistance;
    }

    /**
     * Calculates the total duration of the route.
     *
     * @return The total duration.
     */
    public double calculateTotalDuration() {
        double totalDuration = 0;
        AidBox[] aidboxes = route.getRoute();
        for (int i = 0; i < aidboxes.length - 1; i++) {
            try {
                totalDuration += aidboxes[i].getDuration(aidboxes[i + 1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalDuration;
    }

    /**
     * Calculates the number of non-picked containers in the route.
     *
     * @return The number of non-picked containers.
     */
    public int calculateNonPickedContainers() {
        AidBox[] aidboxes = route.getRoute();
        int count = 0;
        for (int i = 0; i < aidboxes.length; i++) {
            Container[] containers = aidboxes[i].getContainers();
            for (int j = 0; j < containers.length; j++) {
                if (!((ContainerImp) containers[i]).isPicked()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calculates the number of vehicles that were not used in the route.
     *
     * @return The number of not used vehicles.
     */
    private int calculateNotUsedVehicles() {
        Institution[] institutions = (Institution[]) route.getRoute();
        int count = 0;
        for (int i = 0; i < institutions.length; i++) {
            Vehicle[] vehicles = institutions[i].getVehicles();
            for (int j = 0; j < vehicles.length; j++) {
                if (!((VehicleImp) vehicles[j]).isEnable()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Gets the number of vehicles used in the route.
     *
     * @return The number of used vehicles.
     */
    @Override
    public int getUsedVehicles() {
        return usedVehicles;
    }

    /**
     * Gets the number of containers picked during the route.
     *
     * @return The number of picked containers.
     */
    @Override
    public int getPickedContainers() {
        return pickedContainers;
    }

    /**
     * Gets the total distance covered during the route.
     *
     * @return The total distance.
     */
    @Override
    public double getTotalDistance() {
        return totalDistance;
    }

    /**
     * Gets the total duration of the route.
     *
     * @return The total duration.
     */
    @Override
    public double getTotalDuration() {
        return totalDuration;
    }

    /**
     * Gets the number of non-picked containers during the route.
     *
     * @return The number of non-picked containers.
     */
    @Override
    public int getNonPickedContainers() {
        return nonPickedContainers;
    }

    /**
     * Gets the number of vehicles that were not used during the route.
     *
     * @return The number of not used vehicles.
     */
    @Override
    public int getNotUsedVehicles() {
        return notUsedVehicles;
    }

    /**
     * Gets the date when the report was generated.
     *
     * @return The date of the report.
     */
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Returns a string representation of the report.
     *
     * @return A string representation of the report.
     */
    @Override
    public String toString() {
        return "Report{"
                + "date=" + date
                + ", usedVehicles=" + usedVehicles
                + ", pickedContainers=" + pickedContainers
                + ", totalDistance=" + totalDistance
                + ", totalDuration=" + totalDuration
                + ", nonPickedContainers=" + nonPickedContainers
                + ", notUsedVehicles=" + notUsedVehicles
                + '}';
    }

    /**
     * Compares this report with the specified object for equality.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the specified object is equal to this report;
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReportImp other = (ReportImp) obj;
        return usedVehicles == other.usedVehicles
                && pickedContainers == other.pickedContainers
                && Double.compare(other.totalDistance, totalDistance) == 0
                && Double.compare(other.totalDuration, totalDuration) == 0
                && nonPickedContainers == other.nonPickedContainers
                && notUsedVehicles == other.notUsedVehicles
                && date.equals(other.date);
    }

}
