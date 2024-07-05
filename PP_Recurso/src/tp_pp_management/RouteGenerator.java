///*
// * Nome: Roger Nakauchi
// * Número: 8210005
// * Turna: LSIRCT1
// *
// * Nome: Fábio da Cunha
// * Número: 8210619
// * Turna: LSIRCT1
// */
//package tp_pp_management;
//
//import com.estg.core.AidBox;
//import com.estg.core.Institution;
//import com.estg.core.exceptions.AidBoxException;
//import com.estg.pickingManagement.Vehicle;
//import com.estg.pickingManagement.exceptions.RouteException;
//import tp_pp_classes.AidBoxImp;
//import tp_pp_classes.DataManager;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// * Implementation of the RouteGenerator interface.
// *
// * This class generates routes for an institution based on the available
// * vehicles, aid boxes, and containers managed by the DataManager.
// */
//public class RouteGenerator implements com.estg.pickingManagement.RouteGenerator {
//
//
//    public RouteGenerator() {
//
//    }
//
//    /**
//     * Generates the routes for the given institution.
//     *
//     * @param institution the institution to generate the routes for
//     * @return the generated routes
//     */
//    public RouteImp[] generateRoutes(Institution institution) {
//        AidBox[] aidBoxes = institution.getAidBoxes();
//        Vehicle[] vehicles = institution.getVehicles();
//        RouteImp[] routes = new RouteImp[vehicles.length];
//
//        for (int i = 0; i < vehicles.length; i++) {
//            routes[i] = new RouteImp((VehicleImp) vehicles[i]);
//        }
//
//        for (AidBox aidBox : aidBoxes) {
//            try {
//                if (((AidBoxImp) aidBox).hasPerishableContainer() || ((AidBoxImp) aidBox).getCurrentLoadPercentage() > 80) {
//                    boolean added = false;
//                    for (RouteImp route : routes) {
//                        if (route.canVehiclePickAidbox(aidBox)) {
//                            route.addAidBox(aidBox);
//                            added = true;
//                            break;
//                        }
//                    }
//                    if (!added) {
//                        throw new RouteException("No suitable vehicle found for AidBox: " + aidBox.getCode());
//                    }
//                }
//            } catch (AidBoxException | RouteException ex) {
//                Logger.getLogger(RouteGenerator.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return routes;
//    }
//}

