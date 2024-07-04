/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package menu;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Vehicle;
import http.HttpProviderImp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tp_pp_classes.DataManager;
import tp_pp_classes.InstitutionImp;
import tp_pp_classes.MeasurementImp;

public class Menu {

    private Institution inst;
    private AidBox aid;
    private static String filePath;
    private DataManager arrays;

    private BufferedReader reader;
    private static HttpProviderImp httpProvider = new HttpProviderImp();

    public Menu(Institution inst) {
        this.inst = inst;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.arrays = new DataManager();
    }

    public void start() {
        int option = 0;

        do {
            System.out.println("=== Welcome To Felgueiras Institution ===");
            System.out.println("1. Institution");
            System.out.println("2. Routes");
            System.out.println("3. Report");
            System.out.println("4. Exit");
            System.out.println("Option: ");
            try {
                option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        showInstitutionMenu();
                        break;
                    case 2:
                        //showContainerMenu();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Invalid option!");
                        start();
                }

            } catch (IOException e) {
                System.out.println("Error reading input");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (option != 4);
    }

    public void showInstitutionMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Institution Menu ===");
            System.out.println("1. AidBox");
            System.out.println("2. Vehicles");
            System.out.println("3. PickingMaps");
            System.out.println("4. Measurements");
            System.out.println("5. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        showAidBoxMenu();
                        break;
                    case 2:
                        showVehiclesMenu();
                        break;
                    case 3: {
                        try {
                            viewDistances();
                        } catch (AidBoxException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                    case 4:
                        showMeasurementsMenu();
                        break;

                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Try again!\n");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private AidBox[] listAidBox() throws AidBoxException {

        AidBox[] aidboxes = inst.getAidBoxes();

        if (aidboxes == null) {
            throw new AidBoxException();
        } else {
            System.out.println("AidboxList: ");
            for (AidBox aidbox : aidboxes) {
                if (aidbox != null) {
                    System.out.println(aidbox);
                }
            }
            return aidboxes;
        }
    }

    private void addAidBox() throws ContainerException, AidBoxException {

        try {
            System.out.print("Enter the Aid Box code: ");
            String code = reader.readLine();

            AidBox[] aidboxes = arrays.getAidBox();

            boolean found = false;
            for (int i = 0; i < aidboxes.length; i++) {
                if (aidboxes[i] != null && aidboxes[i].getCode().equals(code)) {
                    inst.addAidBox(aidboxes[i]);
                    found = true;
                }
            }
            if (!found) {
                throw new AidBoxException("Aid Box with code '\" + code + \"' not found.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void viewDistances() throws IOException, AidBoxException {

        System.out.print("Enter the from Aid Box code: ");
        String aid1 = reader.readLine();
        System.out.print("Enter the to Aid Box code: ");
        String aid2 = reader.readLine();

        AidBox[] aidboxes = arrays.getAidBox();

        boolean found = false;
        for (int i = 0; i < aidboxes.length; i++) {
            if (aidboxes[i] != null && aidboxes[i].getCode().equals(aid1)) {
                for (int j = 0; j < aidboxes.length; j++) {
                    if (aidboxes[j] != null && aidboxes[j].getCode().equals(aid2)) {
                        System.out.println("Distances: " + aidboxes[i].getDistance(aidboxes[j]));
                    }
                }
                found = true;
            }
        }
        if (!found) {
            throw new AidBoxException("Aid Box with code '\" + code + \"' not found.");
        }
    }

    private void viewDuration() throws IOException, AidBoxException {

        System.out.print("Enter the from Aid Box code: ");
        String aid1 = reader.readLine();
        System.out.print("Enter the to Aid Box code: ");
        String aid2 = reader.readLine();

        AidBox[] aidboxes = arrays.getAidBox();

        boolean found = false;
        for (int i = 0; i < aidboxes.length; i++) {
            if (aidboxes[i] != null && aidboxes[i].getCode().equals(aid1)) {
                for (int j = 0; j < aidboxes.length; j++) {
                    if (aidboxes[j] != null && aidboxes[j].getCode().equals(aid2)) {
                        System.out.println("Duration: " + aidboxes[i].getDuration(aidboxes[j]));
                    }
                }
                found = true;
            }
        }
        if (!found) {
            throw new AidBoxException("Aid Box with code '\" + code + \"' not found.");
        }
    }

    public void showAidBoxMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Aidbox Menu ===");
            System.out.println("1. List Aidbox");
            System.out.println("2. Add Aidbox");
            System.out.println("3. Distances between Aidboxes");
            System.out.println("4. Duration between Aidboxes");
            System.out.println("5. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1: {
                        try {
                            listAidBox();
                        } catch (AidBoxException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                    case 2: {
                        try {
                            addAidBox();
                        } catch (ContainerException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (AidBoxException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                    case 3: {
                        try {
                            viewDistances();
                        } catch (AidBoxException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                    case 4: {
                        try {
                            viewDuration();
                        } catch (AidBoxException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Try again!\n");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    public void showMeasurementsMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Measurements Menu ===");
            System.out.println("1. List Measurements");
            System.out.println("2. Add Measurements");
            System.out.println("3. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1: {
                        try {
                            listMeasurements();
                        } catch (MeasurementException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                    case 2:
                        try {
                            addMeasurements();
                        } catch (MeasurementException e) {
                            throw new RuntimeException(e);
                        } catch (ContainerException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Try again!\n");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private Measurement[] listMeasurements() throws MeasurementException {

        Measurement[] measurements = arrays.getMeasurement();

        if (measurements == null) {
            throw new MeasurementException();
        } else {
            System.out.println("Measurement List: ");
            for (Measurement msmnt : measurements) {
                if (msmnt != null) {
                    System.out.println(msmnt);
                }
            }
            return measurements;
        }

    }

    private void addMeasurements() throws IOException, MeasurementException, ContainerException {

        System.out.println("Enter the Container code: ");
        String containerCode = reader.readLine();

        AidBox[] aidboxes = inst.getAidBoxes();

        int i = 0;
        boolean found = false;
        Container container = null;

        while (i < aidboxes.length && !found) {
            Container[] containers = aidboxes[i].getContainers();
            for (int j = 0; j < containers.length && !found; j++) {
                if (containers[j].getCode().equals(containerCode)) {
                    container = containers[j];
                    found = true;
                }
            }
            i++;
        }

        if (!found) {
            System.out.println("Container not found.");
        }

        Measurement[] measurements = arrays.getMeasurement();

        for (int j = 0; j < measurements.length; j++) {
            if (measurements[j] instanceof MeasurementImp) {
                MeasurementImp measurementImp = (MeasurementImp) measurements[j];
                if (measurementImp.getContainerCode().equals(containerCode)){
                    inst.addMeasurement(measurementImp,container);
                }
            }
        }
        System.out.println("Measurements added successfully.");
    }

    public void showVehiclesMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Vehicles Menu ===");
            System.out.println("1. List Vehicles");
            System.out.println("2. Add Vehicles");
            System.out.println("3. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        try {
                            listVehicles();
                        } catch (VehicleException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        try {
                            addVehicle();
                        } catch (VehicleException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Try again!\n");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private Vehicle[] listVehicles() throws VehicleException {

        Vehicle[] vehicles = inst.getVehicles();

        if (vehicles == null) {
            throw new VehicleException();
        } else {
            System.out.println("Vehicles List: ");
            for (Vehicle vhcl : vehicles) {
                if (vhcl != null) {
                    System.out.println(vhcl);
                }
            }
            return vehicles;
        }

    }

    private void addVehicle() throws VehicleException {

        try {
            System.out.print("Enter the Vehicle code: ");
            String code = reader.readLine();

            Vehicle[] vehicles = arrays.getVehicles();

            boolean found = false;
            for (int i = 0; i < vehicles.length; i++) {
                if (vehicles[i] != null && vehicles[i].getCode().equals(code)) {
                    inst.addVehicle(vehicles[i]);
                    found = true;
                }
            }
            if (!found) {
                throw new VehicleException("Vehicle with code '\" + code + \"' not found.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        InstitutionImp inst = new InstitutionImp("ONG");

        Menu menu = new Menu(inst);
        menu.start();

    }

}
