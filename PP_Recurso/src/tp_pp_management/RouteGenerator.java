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
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import tp_pp_classes.DataManager;

/**
 * Implementation of the RouteGenerator interface.
 *
 * This class generates routes for an institution based on the available
 * vehicles, aid boxes, and containers managed by the DataManager.
 */
public class RouteGenerator implements com.estg.pickingManagement.RouteGenerator {

    /**
     * The data manager that provides access to vehicles, aid boxes, and
     * containers.
     */
    private DataManager dataManager;

    /**
     * Constructs a new RouteGenerator with the specified DataManager.
     * 
     * @param dataManager The data manager that provides access to vehicles, aid boxes, and containers.
     */
    public RouteGenerator(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Generates routes for the specified institution based on the available vehicles
     * and aid boxes managed by the DataManager.
     * 
     * @param instn The institution for which the routes are generated.
     * @return An array of routes.
     */
    @Override
    public Route[] generateRoutes(Institution instn) {

        Vehicle[] vehicles = dataManager.getVehicles();
        AidBox[] aidboxes = dataManager.getAidBox();
        Container[] containers = dataManager.getContainers();
        Route[] routes = new Route[vehicles.length];

        for (int i = 0; i < vehicles.length; i++) {
            routes[i] = new RouteImp(vehicles[i]);
        }

        int routeIndex = 0;
        for (int i = 0; i < aidboxes.length; i++) {
            AidBox aidBox = aidboxes[i];
            boolean added = false;

            while (!added) {
                try {
                    routes[routeIndex].addAidBox(aidBox);
                    added = true;
                } catch (RouteException e) {
                    routeIndex = (routeIndex + 1) % routes.length; //Tenta add o aidbox na próxima rota (uma posição acima, no array, comparado com a anterior)
                }
            }
        }
        return routes;
    }

}
