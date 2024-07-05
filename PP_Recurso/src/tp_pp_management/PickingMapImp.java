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

import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Route;
import java.time.LocalDateTime;

/**
 * Implementation of the PickingMap interface.
 *
 * This class represents a picking map with a specific date and an array of
 * routes. It provides methods to retrieve the date and the routes of the
 * picking map.
 */
public class PickingMapImp implements PickingMap {

    /**
     * The date of the picking map.
     */
    private LocalDateTime date;

    private static int INICIALIZE_ROUTE = 5;

    /**
     * The routes associated with the picking map.
     */
    private Route[] routes;

    private int nRoutes;

    /**
     * Constructs a new PickingMapImp instance with the specified date and
     * routes.
     *
     * @param date The date of the picking map.
     */
    public PickingMapImp(LocalDateTime date) {
        this.date = date;
        this.routes = new RouteImp[INICIALIZE_ROUTE];
        this.nRoutes = 0;
    }

    /**
     * Gets the date of the picking map.
     *
     * @return The date of the picking map.
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Gets the routes associated with the picking map.
     *
     * @return The routes associated with the picking map.
     */
    @Override
    public Route[] getRoutes() {
        return this.routes;
    }

    public void setRoutes(Route[] routes){

        for ( int i = 0; i < routes.length; i++){
            if ( routes[i] != null){
                this.nRoutes++;
            }
        }

        if(this.nRoutes == this.routes.length){
            this.routes = new Route[nRoutes];
        }

        this.routes = routes;
    }

}
