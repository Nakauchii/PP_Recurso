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
import com.estg.core.exceptions.InstitutionException;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import tp_pp_classes.ContainerImp;
import tp_pp_classes.DataManager;

/**
 * Implementation of the RouteGenerator interface.
 *
 * This class generates routes for an institution based on the available
 * vehicles, aid boxes, and containers managed by the DataManager.
 */
public class RouteGenerator implements com.estg.pickingManagement.RouteGenerator {


    @Override
    public Route[] generateRoutes(Institution institution) {
        return new Route[0];
    }
}
