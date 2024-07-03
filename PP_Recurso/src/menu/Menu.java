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
            System.out.println("3. Exit");
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
                    default:
                        System.out.println("Invalid option!");
                        start();
                }

            } catch (IOException e) {
                System.out.println("Error reading input");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (option != 3);
    }

    
    public void showInstitutionMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Institution Menu ===");
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

                    case 4:
                    {
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

    public static void main(String[] args) {
        InstitutionImp inst = new InstitutionImp("ONG");

        Menu menu = new Menu(inst);
        menu.start();

    }

}
