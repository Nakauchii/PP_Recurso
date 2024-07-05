package pp_recurso;

import com.estg.core.*;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import tp_pp_classes.*;
//import tp_pp_management.RouteGenerator;

public class PP_Recurso {

    public static void main(String[] args) {
//        try {
//            DataManager dataManager = new DataManager();
//            Measurement[] msrmnt = dataManager.getMeasurement();
//            AidBox[] aidBox = dataManager.getAidBox();
//            Container[] container = dataManager.getContainers();
//            Vehicle[] vehicles = dataManager.getVehicles();
//            Institution ins = new InstitutionImp("Ong");
//
//            // Adicionando dados à instituição ins
//            ins.addAidBox(aidBox[1]);
//            ins.addAidBox(aidBox[2]);
//            ins.addAidBox(aidBox[3]);
//            ins.addAidBox(aidBox[4]);
//            ins.addMeasurement(msrmnt[16], container[7]);
//            ins.addMeasurement(msrmnt[5], container[19]);
//            ins.addMeasurement(msrmnt[31], container[31]);
//            ins.addVehicle(vehicles[1]);
//            ins.addVehicle(vehicles[3]);
//
//            // Criando uma instância do RouteGenerator
////            RouteGenerator routeGenerator = new RouteGenerator();
////
////            // Gerando as rotas com base na instituição ins
////            Route[] routes = routeGenerator.generateRoutes(ins);
//
//            // Iterando pelas rotas geradas e imprimindo detalhes
//            for (int i = 0; i < routes.length; i++) {
//                Route route = routes[i];
//                System.out.println("Rota " + (i + 1) + ": " + route.toString());
//
//                // Adicione mais detalhes da rota conforme necessário
//                // Exemplo: imprimir os veículos e caixas de auxílio associados a cada rota
//                Vehicle[] vehiclesInRoute = new Vehicle[]{route.getVehicle()};
//                AidBox[] aidBoxesInRoute = route.getRoute();
//                System.out.println("Veículos na rota:");
//                for (Vehicle vehicle : vehiclesInRoute) {
//                    System.out.println("- " + vehicle.toString());
//                }
//                System.out.println("Caixas de auxílio na rota:");
//                for (AidBox Box : aidBoxesInRoute) {
//                    System.out.println("- " + Box.toString());
//                }
//            }
//
//        } catch (ContainerException | AidBoxException | VehicleException | MeasurementException ex) {
//            ex.printStackTrace();
//        }
    }
}
