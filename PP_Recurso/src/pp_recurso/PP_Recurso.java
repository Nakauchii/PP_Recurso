/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pp_recurso;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import tp_pp_classes.AidBoxImp;
import tp_pp_classes.ContainerImp;
import tp_pp_classes.DataManager;
import tp_pp_classes.LocationImp;

/**
 *
 * @author Roger Nakauchi
 */
public class PP_Recurso {

    public static void main(String[] args) throws ContainerException, AidBoxException {

        DataManager dataManager = new DataManager();
        AidBox[] aidboxes = dataManager.getAidBox();
        for (AidBox aidbox : aidboxes) {
            if (aidbox != null) {
                System.out.println(aidbox);
            }
        }

        System.out.println(((AidBoxImp)aidboxes[0]).getDistance(aidboxes[1]));
    }
}
