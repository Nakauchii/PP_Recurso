/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_management;

import com.estg.core.AidBox;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;

/**
 *
 * @author fabio
 */
public class Route implements com.estg.pickingManagement.Route {

    @Override
    public void addAidBox(AidBox aidBox) throws RouteException {
        if (aidBox == null){
            return false;
        }

    }

    @Override
    public AidBox removeAidBox(AidBox aidBox) throws RouteException {
        return null;
    }

    @Override
    public boolean containsAidBox(AidBox aidBox) {
        return false;
    }

    @Override
    public void replaceAidBox(AidBox aidBox, AidBox aidBox1) throws RouteException {

    }

    @Override
    public void insertAfter(AidBox aidBox, AidBox aidBox1) throws RouteException {

    }

    @Override
    public AidBox[] getRoute() {
        return new AidBox[0];
    }

    @Override
    public Vehicle getVehicle() {
        return null;
    }

    @Override
    public double getTotalDistance() {
        return 0;
    }

    @Override
    public double getTotalDuration() {
        return 0;
    }

    @Override
    public Report getReport() {
        return null;
    }
}
