/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 *
 * @author fabio
 */
public class RouteGenerator implements com.estg.pickingManagement.RouteGenerator {

    private DataManager dataManager;

    public RouteGenerator(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    @Override
    public Route[] generateRoutes(Institution instn) {
        
        Vehicle[] vehicles = dataManager.getVehicles();
        AidBox[] aidboxes = dataManager.getAidBox();
        Container[] containers = dataManager.getContainers();
        Route[] routes = new Route[vehicles.length];
        
        for(int i = 0; i < vehicles.length; i++) {
            routes[i] = new RouteImp(vehicles[i]);
        }
        
        int routeIndex = 0;
        for(int i = 0; i < aidboxes.length; i++) {
            AidBox aidBox = aidboxes[i];
            boolean added = false;
            
            while(!added) {
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
